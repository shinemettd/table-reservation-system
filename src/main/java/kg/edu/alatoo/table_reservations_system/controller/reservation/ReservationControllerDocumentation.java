package kg.edu.alatoo.table_reservations_system.controller.reservation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.edu.alatoo.table_reservations_system.payload.reservation.ReservationCreateDTO;
import kg.edu.alatoo.table_reservations_system.payload.reservation.ReservationDTO;
import kg.edu.alatoo.table_reservations_system.payload.reservation.ReservationEditDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/reservation")
@Tag(name = "Reservation's API", description = "Operations for managing reservations")
public sealed interface ReservationControllerDocumentation permits ReservationController {

    @PostMapping("/create-reservation")
    @Operation(summary = "Creating a reservation", description = "Default creating a reservation with DTO")
    ResponseEntity<ReservationDTO> create(@RequestBody ReservationCreateDTO dto);

    @GetMapping("/user")
    @Operation(summary = "Get current user reservations", description = "Getting current user reservations if signed in")
    ResponseEntity<Set<ReservationDTO>> getAllUserReservations();

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user reservations", description = "Getting user reservations by user id")
    ResponseEntity<Set<ReservationDTO>> getAllUserReservationsById(@PathVariable Long userId);

    @PutMapping("/{reservationId}")
    @Operation(summary = "Edit reservation", description = "Edit reservation by its id and edit DTO")
    ResponseEntity<ReservationDTO> editReservationById(
            @PathVariable Long reservationId,
            @RequestBody ReservationEditDTO dto);

    @PostMapping("/{reservationId}/cancel")
    @Operation(summary = "Cancel reservation", description = "Canceling reservations by its id, if current user its author")
    ResponseEntity<ReservationDTO> cancel(@PathVariable Long reservationId);

}
