package kg.edu.alatoo.table_reservations_system.payload.reservation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public record SelfReservationCreateDTO(
        @Min(1)
        Long tableId,
        @NotEmpty
        LocalDateTime reservationStartTime,
        LocalDateTime reservationEndTime
) {
}
