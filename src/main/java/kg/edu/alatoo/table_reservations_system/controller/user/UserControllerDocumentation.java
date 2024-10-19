package kg.edu.alatoo.table_reservations_system.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.edu.alatoo.table_reservations_system.enums.Role;
import kg.edu.alatoo.table_reservations_system.payload.user.UserDTO;
import kg.edu.alatoo.table_reservations_system.payload.user.UserUpdateRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@Tag(name = "User's API", description = "Operations for managing users (in particular for admins)")
public sealed interface UserControllerDocumentation permits UserController {

    @PostMapping("/update/self")
    @Operation(summary = "Permits user to change his data", description = "Permits user to change his data depending on gotten DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success self-update"),
            @ApiResponse(responseCode = "403", description = "Request without session (if jwt somehow not worked)")
    })
    ResponseEntity<UserDTO> selfUpdate(@RequestBody UserUpdateRequestDTO dto);

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update/{id}")
    @Operation(summary = "Permits admin to change user data by his id", description = "Permits admin to change user data by user id depending on gotten DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success update"),
            @ApiResponse(responseCode = "403", description = "No permission to update other users"),
            @ApiResponse(responseCode = "404", description = "User to update not found")
    })
    ResponseEntity<UserDTO> update(@PathVariable("id") Long id, UserUpdateRequestDTO dto);

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update-role/{userId}/{role}")
    @Operation(summary = "Permits admin to change users role", description = "Permits admin to change user role by user id depending on role in path")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success update"),
            @ApiResponse(responseCode = "403", description = "No permission to update role"),
            @ApiResponse(responseCode = "404", description = "User to update not found")
    })
    ResponseEntity<UserDTO> updateUserRole(@PathVariable("userId") Long userId, @PathVariable("role") Role role);

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Permits admin to change users role", description = "Permits admin to change user role by user id depending on role in path")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success delete"),
            @ApiResponse(responseCode = "403", description = "No permission to delete users"),
            @ApiResponse(responseCode = "404", description = "User to delete not found")
    })
    ResponseEntity<Void> delete(@PathVariable("id") Long id);

}
