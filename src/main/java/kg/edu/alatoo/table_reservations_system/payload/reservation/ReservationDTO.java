package kg.edu.alatoo.table_reservations_system.payload.reservation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReservationDTO(
        Long userId,
        Long tableId,
        LocalDateTime reservationStartTime,
        LocalDateTime reservationEndTime,
        LocalDateTime deletionDate
) {
}
