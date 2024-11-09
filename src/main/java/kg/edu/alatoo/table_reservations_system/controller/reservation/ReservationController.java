package kg.edu.alatoo.table_reservations_system.controller.reservation;

import kg.edu.alatoo.table_reservations_system.payload.reservation.ReservationCreateDTO;
import kg.edu.alatoo.table_reservations_system.payload.reservation.ReservationDTO;
import kg.edu.alatoo.table_reservations_system.payload.reservation.ReservationEditDTO;
import kg.edu.alatoo.table_reservations_system.service.ReservationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class ReservationController implements ReservationControllerDocumentation {

    ReservationService service;

    @Override
    public ResponseEntity<ReservationDTO> create(ReservationCreateDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Override
    public ResponseEntity<Set<ReservationDTO>> getAllUserReservations() {
        return ResponseEntity.ok(service.getAllUserReservations());
    }

    @Override
    public ResponseEntity<Set<ReservationDTO>> getAllUserReservationsById(Long userId) {
        return ResponseEntity.ok(service.getAllUserReservationsById(userId));
    }

    @Override
    public ResponseEntity<ReservationDTO> editReservationById(Long reservationId, ReservationEditDTO dto) {
        return ResponseEntity.ok(service.editReservationById(reservationId, dto));
    }

    @Override
    public ResponseEntity<ReservationDTO> cancel(Long reservationId) {
        return ResponseEntity.ok(service.cancel(reservationId));
    }

}
