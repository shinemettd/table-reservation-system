package kg.edu.alatoo.table_reservations_system.payload.table;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TableDTO(
        Long id,
        Long restaurantId,
        String restaurantName,
        Long number,
        Long capacity,
        LocalDateTime deletionDate
) {
}
