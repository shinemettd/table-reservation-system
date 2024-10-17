package kg.edu.alatoo.table_reservations_system.payload.user;

import jakarta.validation.constraints.NotBlank;

public record UserRegisterRequestDTO (
        @NotBlank(message = "Username is empty")
        String username,
        @NotBlank(message = "Password is empty")
        String password,
        @NotBlank(message = "Full name is empty")
        String fullName,
        @NotBlank(message = "Phone number is empty")
        String phoneNumber
) {
}
