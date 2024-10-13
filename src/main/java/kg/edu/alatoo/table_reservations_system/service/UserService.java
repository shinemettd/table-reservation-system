package kg.edu.alatoo.table_reservations_system.service;

import kg.edu.alatoo.table_reservations_system.entity.User;
import kg.edu.alatoo.table_reservations_system.enums.Role;
import kg.edu.alatoo.table_reservations_system.payload.user.*;

public interface UserService {
    UserDTO register(UserRegisterRequestDTO dto);
    UserLoginResponseDTO login(UserLoginRequestDTO dto);
    UserDTO update(UserUpdateRequestDTO dto);
    void delete(Long id);
    User getCurrentUser();
    Role getCurrentUserRole();
}
