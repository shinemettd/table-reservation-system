package kg.edu.alatoo.table_reservations_system.payload.user;

import jakarta.validation.constraints.NotNull;

public record UserUpdateRequestDTO (
        @NotNull Long id,
        String username,
        String password,
        String fullName,
        String phoneNumber
) {
}
