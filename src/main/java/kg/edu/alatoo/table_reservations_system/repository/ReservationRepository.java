package kg.edu.alatoo.table_reservations_system.repository;

import kg.edu.alatoo.table_reservations_system.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.user.id = :userId")
    Set<Reservation> getAllByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM Reservation r WHERE r.user.id = :userId AND r.deletionDate IS NULL")
    Set<Reservation> getAllNonDeletedByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM Reservation r WHERE r.table.id = :tableId " +
            "AND :startDate >= r.reservationStartTime " +
            "AND :endDate <= r.reservationEndTime " +
            "AND r.deletionDate IS NULL")
    Optional<Reservation> getReservationByTableIdAndDatesBetween(@Param("tableId") Long tableId,
                            @Param("startDate") LocalDateTime startDate,
                            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT r FROM Reservation r WHERE r.reservationEndTime >= CURRENT_TIMESTAMP AND r.deletionDate IS NULL")
    Set<Reservation> getReservationsByAfterCurrentDate();

    @Query("SELECT r FROM Reservation r WHERE r.reservationEndTime IS NULL " +
            "AND r.reservationStartTime >= CURRENT_DATE - 1 " +
            "AND r.deletionDate IS NULL")
    Set<Reservation> getReservationFromPreviousDayWhereEndTimeIsNull();

}
