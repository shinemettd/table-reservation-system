package kg.edu.alatoo.table_reservations_system.payload.user;

public record UserUpdateRequestDTO (
        String username,
        String password,
        String fullName,
        String phoneNumber
) {
}
