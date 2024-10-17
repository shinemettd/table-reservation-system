package kg.edu.alatoo.table_reservations_system.payload.user;

import jakarta.validation.constraints.NotBlank;

/**
 * User can use phone number or username as login
 */
public record UserLoginRequestDTO(
        @NotBlank(message = "Login is empty")
        String login,
        @NotBlank(message = "Password is empty")
        String password
) {
}
