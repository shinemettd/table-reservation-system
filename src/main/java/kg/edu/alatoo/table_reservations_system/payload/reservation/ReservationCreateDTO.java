package kg.edu.alatoo.table_reservations_system.payload.reservation;

import java.time.LocalDateTime;

public record ReservationCreateDTO(
        Long userId,
        Long tableId,
        Long peopleCount,
        LocalDateTime reservationStartTime,
        LocalDateTime reservationEndTime
) {
}
