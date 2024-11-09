package kg.edu.alatoo.table_reservations_system.service.impl;

import kg.edu.alatoo.table_reservations_system.entity.User;
import kg.edu.alatoo.table_reservations_system.enums.Role;
import kg.edu.alatoo.table_reservations_system.exceptions.AuthException;
import kg.edu.alatoo.table_reservations_system.exceptions.NoPermissionException;
import kg.edu.alatoo.table_reservations_system.exceptions.NotFoundException;
import kg.edu.alatoo.table_reservations_system.exceptions.ReservationAlreadyExists;
import kg.edu.alatoo.table_reservations_system.mapper.ReservationMapper;
import kg.edu.alatoo.table_reservations_system.payload.reservation.ReservationCreateDTO;
import kg.edu.alatoo.table_reservations_system.payload.reservation.ReservationDTO;
import kg.edu.alatoo.table_reservations_system.payload.reservation.ReservationEditDTO;
import kg.edu.alatoo.table_reservations_system.payload.reservation.SelfReservationCreateDTO;
import kg.edu.alatoo.table_reservations_system.repository.ReservationRepository;
import kg.edu.alatoo.table_reservations_system.service.ReservationService;
import kg.edu.alatoo.table_reservations_system.service.TableService;
import kg.edu.alatoo.table_reservations_system.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static kg.edu.alatoo.table_reservations_system.service.impl.UserServiceImpl.getCurrentUser;
import static kg.edu.alatoo.table_reservations_system.service.impl.UserServiceImpl.getCurrentUserRole;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReservationServiceImpl implements ReservationService {

    ReservationRepository repository;

    ReservationMapper mapper;

    TableService tableService;

    UserService userService;

    @Override
    public ReservationDTO selfReservation(SelfReservationCreateDTO dto) {
        User currentUser = getCurrentUser();
        if (currentUser == null)
            throw new AuthException("User not authenticated");

        this.checkForExistReservation(dto.tableId(), dto.reservationStartTime(), dto.reservationEndTime());

        var reservation = mapper.toEntity(dto);
        reservation.setUser(currentUser);
        reservation.setTable(tableService.getEntityById(dto.tableId()));
        return mapper.toDto(repository.save(reservation));
    }

    @Override
    public ReservationDTO create(ReservationCreateDTO dto) {
        this.checkForExistReservation(dto.tableId(), dto.reservationStartTime(), dto.reservationEndTime());

        Role currentUserRole = getCurrentUserRole();
        return switch(currentUserRole) {
            case ADMIN -> {
                var newReservation = mapper.toEntity(dto);
                newReservation.setUser(userService.getEntityById(dto.tableId()));
                newReservation.setTable(tableService.getEntityById(dto.tableId()));
                yield mapper.toDto(repository.save(mapper.toEntity(dto)));
            }
            case null, default -> throw new NoPermissionException();
        };
    }

    @Override
    public Set<ReservationDTO> getAllUserReservations() {
        User user = getCurrentUser();
        if (user == null || user.getRole() == null)
            throw new AuthException("User not authenticated");
        return this.getAllUserReservationsById(user.getId());
    }

    @Override
    public Set<ReservationDTO> getAllUserReservationsById(Long userId) {
        Role currentUserRole = getCurrentUserRole();
        return switch(currentUserRole) {
            case ADMIN -> repository.getAllByUserId(userId).stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toSet());
            case null, default -> repository.getAllNonDeletedByUserId(userId).stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toSet());
        };
    }

    @Override
    @Transactional
    public ReservationDTO editReservationById(Long reservationId, ReservationEditDTO dto) {
        this.checkForExistReservation(dto.tableId(), dto.reservationStartTime(), dto.reservationEndTime());

        var reservation = repository.findById(reservationId)
                .orElseThrow(NotFoundException::new);
        reservation.setTable(tableService.getEntityById(dto.tableId()));
        reservation.setReservationStartTime(dto.reservationStartTime());
        reservation.setReservationEndTime(dto.reservationEndTime());
        return mapper.toDto(reservation);
    }

    @Override
    @Transactional
    public ReservationDTO cancel(Long reservationId) {
        var reservation = repository.findById(reservationId)
                .orElseThrow(NotFoundException::new);
        User currentUser = getCurrentUser();
        if (reservationId.equals(currentUser.getId()) || Role.ADMIN.equals(currentUser.getRole())) {
            reservation.setDeletionDate(LocalDateTime.now());
            return mapper.toDto(reservation);
        }
        throw new NoPermissionException("You cannot cancel this reservation");
    }

    @Override
    @Transactional
    public void deleteExpiredReservations() {
        var reservations = repository.getReservationsByAfterCurrentDate();
        reservations.forEach(r -> r.setDeletionDate(LocalDateTime.now()));
    }

    @Override
    public void deleteLongReservationsFromPreviousDayWithoutEndDate() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        var reservations = repository.getReservationFromDayWhereEndTimeIsNull(yesterday);
        reservations.forEach(r -> r.setDeletionDate(LocalDateTime.now()));
    }

    private void checkForExistReservation(Long tableId, LocalDateTime startTime, LocalDateTime endTime) {
        var optionalReservation = repository.getReservationByTableIdAndDatesBetween(tableId, startTime, endTime);
        if (optionalReservation.isPresent()) {
            throw new ReservationAlreadyExists(String.format("Reservation from %s to %s is already taken", startTime, endTime));
        }
    }

}