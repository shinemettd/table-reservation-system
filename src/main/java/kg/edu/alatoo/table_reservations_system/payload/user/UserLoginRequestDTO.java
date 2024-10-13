package kg.edu.alatoo.table_reservations_system.payload.user;

/**
 * User can use phone number or username as login
 */
public record UserLoginRequestDTO(
        String login,
        String password
) {
}
