package kg.edu.alatoo.table_reservations_system.payload.restaurant;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import kg.edu.alatoo.table_reservations_system.payload.table.TableDTO;

import java.time.LocalDateTime;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RestaurantDTO(
        @Min(value = 1, message = "id must be not negative!")
        Long id,
        @NotEmpty(message = "name must be not empty!")
        String name,
        Set<TableDTO> tables,
        LocalDateTime deletionDate
) {
}
