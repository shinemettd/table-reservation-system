package kg.edu.alatoo.table_reservations_system.payload.user;

public record UserLoginResponseDTO(
        String username,
        String role,
        String accessToken
) {
}
