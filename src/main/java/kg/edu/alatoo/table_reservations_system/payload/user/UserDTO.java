package kg.edu.alatoo.table_reservations_system.payload.user;

public record UserDTO(
        String username,
        String fullName,
        String phoneNumber,
        String role
) {
}
