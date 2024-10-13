package kg.edu.alatoo.table_reservations_system.mapper;

import kg.edu.alatoo.table_reservations_system.entity.User;
import kg.edu.alatoo.table_reservations_system.payload.user.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User u);

    UserLoginResponseDTO toLoginResponseDTO(User u);

    default UserLoginResponseDTO toLoginResponseDTOWithToken(User u, String token) {
        UserLoginResponseDTO dto = toLoginResponseDTO(u);
        return new UserLoginResponseDTO(dto.username(), dto.role(), token);
    }

    User toEntity(UserDTO dto);

    User toEntity(UserLoginRequestDTO dto);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "fullName", target = "fullName")
    User toEntity(UserRegisterRequestDTO dto);

    User toEntity(UserUpdateRequestDTO dto);

}
