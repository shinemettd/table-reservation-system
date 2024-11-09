package kg.edu.alatoo.table_reservations_system.payload.reservation;

import java.time.LocalDateTime;

public record ReservationEditDTO(
        Long tableId,
        LocalDateTime reservationStartTime,
        LocalDateTime reservationEndTime
) {
}
