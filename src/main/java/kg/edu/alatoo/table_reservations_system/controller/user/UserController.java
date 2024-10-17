package kg.edu.alatoo.table_reservations_system.controller.user;

import kg.edu.alatoo.table_reservations_system.payload.user.UserDTO;
import kg.edu.alatoo.table_reservations_system.payload.user.UserUpdateRequestDTO;
import kg.edu.alatoo.table_reservations_system.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class UserController {

    UserService service;

    @PostMapping("/update/self")
    ResponseEntity<UserDTO> selfUpdate(@RequestBody UserUpdateRequestDTO dto) {
        return new ResponseEntity<>(service.selfUpdate(dto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update/{id}")
    ResponseEntity<UserDTO> update(@PathVariable("id") Long id, UserUpdateRequestDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.softDelete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
