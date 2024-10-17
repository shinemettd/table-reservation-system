package kg.edu.alatoo.table_reservations_system.controller.auth;

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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class AuthController implements AuthControllerDocumentation {

    UserService service;

    @Override
    public ResponseEntity<UserDTO> register(UserRegisterRequestDTO dto) {
        return new ResponseEntity<>(service.register(dto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserLoginResponseDTO> login(UserLoginRequestDTO dto) {
        return new ResponseEntity<>(service.login(dto), HttpStatus.OK);
    }

}
