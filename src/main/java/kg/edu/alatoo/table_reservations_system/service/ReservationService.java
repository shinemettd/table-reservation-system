package kg.edu.alatoo.table_reservations_system.service;

import kg.edu.alatoo.table_reservations_system.payload.reservation.ReservationCreateDTO;
import kg.edu.alatoo.table_reservations_system.payload.reservation.ReservationDTO;
import kg.edu.alatoo.table_reservations_system.payload.reservation.ReservationEditDTO;
import kg.edu.alatoo.table_reservations_system.payload.reservation.SelfReservationCreateDTO;

import java.util.Set;

public interface ReservationService {
    ReservationDTO selfReservation(SelfReservationCreateDTO dto);
    ReservationDTO create(ReservationCreateDTO reservationDTO);
    Set<ReservationDTO> getAllUserReservations();
    Set<ReservationDTO> getAllUserReservationsById(Long userId);
    ReservationDTO editReservationById(Long reservationId, ReservationEditDTO dto);
    ReservationDTO cancel(Long reservationId);
    void deleteExpiredReservations();
    void deleteLongReservationsFromPreviousDayWithoutEndDate();
}
