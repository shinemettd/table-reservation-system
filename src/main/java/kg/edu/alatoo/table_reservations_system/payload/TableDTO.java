package kg.edu.alatoo.table_reservations_system.payload;

public record TableDTO(
        Long restaurantId,
        Long number,
        Long capacity
) {
}
