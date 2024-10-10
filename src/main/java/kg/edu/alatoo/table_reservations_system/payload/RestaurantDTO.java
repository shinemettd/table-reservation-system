package kg.edu.alatoo.table_reservations_system.payload;

import java.util.Set;

public record RestaurantDTO(
        Long id,
        String name,
        Set<TableDTO> tables
) {
}
