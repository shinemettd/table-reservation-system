package kg.edu.alatoo.table_reservations_system.payload.user;

import jakarta.validation.constraints.NotNull;

public record UserRegisterRequestDTO (
        @NotNull(message = "Username is empty")
        String username,
        @NotNull(message = "Password is empty")
        String password,
        @NotNull(message = "Full name is empty")
        String fullName,
        @NotNull(message = "Phone number is empty")
        String phoneNumber
) {
}
