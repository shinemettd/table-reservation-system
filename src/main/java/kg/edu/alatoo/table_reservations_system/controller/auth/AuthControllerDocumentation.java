package kg.edu.alatoo.table_reservations_system.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.edu.alatoo.table_reservations_system.payload.user.UserDTO;
import kg.edu.alatoo.table_reservations_system.payload.user.UserLoginRequestDTO;
import kg.edu.alatoo.table_reservations_system.payload.user.UserLoginResponseDTO;
import kg.edu.alatoo.table_reservations_system.payload.user.UserRegisterRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
@Tag(name = "Auth API", description = "Operations for auth")
public sealed interface AuthControllerDocumentation permits AuthController {

    @PostMapping("/register")
    @Operation(summary = "Register/Sign-up", description = "Default user register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success register"),
            @ApiResponse(responseCode = "400", description = "Not valid values for registration"),
            @ApiResponse(responseCode = "400", description = "Already taken unique values (username or phone number)")
    })
    ResponseEntity<UserDTO> register(@Valid @RequestBody UserRegisterRequestDTO dto);

    @PostMapping("/login")
    @Operation(summary = "Login/Sign-in", description = "Default login with JWT token receive")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success login"),
            @ApiResponse(responseCode = "400", description = "Wrong password"),
            @ApiResponse(responseCode = "404", description = "User does not exist")
    })
    ResponseEntity<UserLoginResponseDTO> login(@Valid @RequestBody UserLoginRequestDTO dto);

}
