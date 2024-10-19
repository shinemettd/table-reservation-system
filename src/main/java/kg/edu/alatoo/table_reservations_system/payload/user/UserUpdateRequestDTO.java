package kg.edu.alatoo.table_reservations_system.payload.user;

public record UserUpdateRequestDTO (
        String newUsername,
        String newPassword,
        String newFullName,
        String newPhoneNumber
) {
}
