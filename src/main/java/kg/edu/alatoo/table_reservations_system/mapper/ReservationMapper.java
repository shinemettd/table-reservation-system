package kg.edu.alatoo.table_reservations_system.mapper;

import kg.edu.alatoo.table_reservations_system.entity.Reservation;
import kg.edu.alatoo.table_reservations_system.entity.TableEntity;
import kg.edu.alatoo.table_reservations_system.entity.User;
import kg.edu.alatoo.table_reservations_system.payload.reservation.ReservationCreateDTO;
import kg.edu.alatoo.table_reservations_system.payload.reservation.ReservationDTO;
import kg.edu.alatoo.table_reservations_system.payload.reservation.SelfReservationCreateDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationDTO toDto(Reservation reservation);
    Reservation toEntity(ReservationDTO reservationDTO);
    Reservation toEntity(SelfReservationCreateDTO dto);
    Reservation toEntity(ReservationCreateDTO dto);
}