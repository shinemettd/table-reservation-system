package kg.edu.alatoo.table_reservations_system.payload.table;

import java.util.List;

public record CreateRestaurantTablesDTO(
        List<TablePropertiesDTO> tables
) {
}
