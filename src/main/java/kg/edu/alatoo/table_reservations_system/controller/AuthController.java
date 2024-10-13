package kg.edu.alatoo.table_reservations_system.controller;

import jakarta.validation.Valid;
import kg.edu.alatoo.table_reservations_system.payload.user.UserDTO;
import kg.edu.alatoo.table_reservations_system.payload.user.UserLoginRequestDTO;
import kg.edu.alatoo.table_reservations_system.payload.user.UserLoginResponseDTO;
import kg.edu.alatoo.table_reservations_system.payload.user.UserRegisterRequestDTO;
import kg.edu.alatoo.table_reservations_system.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    UserService service;

    @PostMapping("/register")
    ResponseEntity<UserDTO> register(@Valid @RequestBody UserRegisterRequestDTO dto) {
        return new ResponseEntity<>(service.register(dto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    ResponseEntity<UserLoginResponseDTO> login(@Valid @RequestBody UserLoginRequestDTO dto) {
        return new ResponseEntity<>(service.login(dto), HttpStatus.OK);
    }

}
