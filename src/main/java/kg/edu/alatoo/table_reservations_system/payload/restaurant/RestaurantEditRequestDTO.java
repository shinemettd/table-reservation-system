package kg.edu.alatoo.table_reservations_system.payload.restaurant;

import jakarta.validation.constraints.NotEmpty;

public record RestaurantEditRequestDTO(
        @NotEmpty(message = "name must be not empty!")
        String name
) {
}
