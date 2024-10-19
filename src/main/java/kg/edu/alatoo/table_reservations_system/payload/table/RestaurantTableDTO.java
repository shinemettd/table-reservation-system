package kg.edu.alatoo.table_reservations_system.payload.table;

public record RestaurantTableDTO(
        Long restaurantId,
        Long tableNumber,
        Long capacity
) {
}
