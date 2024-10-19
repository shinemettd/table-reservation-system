package kg.edu.alatoo.table_reservations_system.payload.restaurant;

import com.fasterxml.jackson.annotation.JsonInclude;
import kg.edu.alatoo.table_reservations_system.payload.table.TableDTO;

import java.time.LocalDateTime;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RestaurantDTO(
        Long id,
        String name,
        Set<TableDTO> tables,
        LocalDateTime deletionDate
) {
}
