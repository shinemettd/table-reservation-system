package kg.edu.alatoo.table_reservations_system.payload.errors;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(
        List<String> messages,
        LocalDateTime time,
        int code
) {
}
