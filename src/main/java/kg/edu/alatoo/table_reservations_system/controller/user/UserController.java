package kg.edu.alatoo.table_reservations_system.controller.user;

import kg.edu.alatoo.table_reservations_system.enums.Role;
import kg.edu.alatoo.table_reservations_system.payload.user.UserDTO;
import kg.edu.alatoo.table_reservations_system.payload.user.UserUpdateRequestDTO;
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
public final class UserController implements UserControllerDocumentation {

    UserService service;

    @Override
    public ResponseEntity<UserDTO> selfUpdate(UserUpdateRequestDTO dto) {
        return new ResponseEntity<>(service.selfUpdate(dto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDTO> update(Long id, UserUpdateRequestDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDTO> updateUserRole(Long userId, Role role) {
        return new ResponseEntity<>(service.updateUserRole(userId, role), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        service.softDelete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
