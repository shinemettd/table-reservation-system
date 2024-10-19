package kg.edu.alatoo.table_reservations_system.payload.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDTO(
        String username,
        String fullName,
        String phoneNumber,
        String role,
        LocalDateTime deletionDate
) {
}
