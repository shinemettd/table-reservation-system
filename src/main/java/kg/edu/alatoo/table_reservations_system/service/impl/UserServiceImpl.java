package kg.edu.alatoo.table_reservations_system.service.impl;

import kg.edu.alatoo.table_reservations_system.entity.User;
import kg.edu.alatoo.table_reservations_system.enums.Role;
import kg.edu.alatoo.table_reservations_system.exceptions.*;
import kg.edu.alatoo.table_reservations_system.mapper.UserMapper;
import kg.edu.alatoo.table_reservations_system.payload.user.*;
import kg.edu.alatoo.table_reservations_system.repository.UserRepository;
import kg.edu.alatoo.table_reservations_system.service.UserService;
import kg.edu.alatoo.table_reservations_system.util.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserMapper mapper;

    JwtUtil jwtUtil;

    PasswordEncoder passwordEncoder;

    UserRepository repository;

    AuthenticationManager authenticationManager;

    @Override
    public UserDTO register(UserRegisterRequestDTO dto) {

        boolean isUsernameTaken = repository.existsUserByUsername(dto.username());
        if (isUsernameTaken) throw new UsernameAlreadyTakenException();

        boolean isPhoneNumberTaken = repository.existsUserByPhoneNumber(dto.phoneNumber());
        if (isPhoneNumberTaken) throw new PhoneNumberAlreadyTakenException();

        User user = mapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.CONSUMER);

        try {
            repository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(format("Cannot register user: %s", e.getMessage()));
        }
        return mapper.toDTO(user);
    }

    @Override
    public UserLoginResponseDTO login(UserLoginRequestDTO dto) {
        User user = repository.findByLogin(dto.login())
                .orElseThrow(() -> new NotFoundException("User does not exists"));
        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new AuthException("Incorrect password!");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    dto.login(),
                    dto.password()
        ));
        String jwtToken = jwtUtil.generateToken(user);
        return mapper.toLoginResponseDTOWithToken(user, jwtToken);
    }

    @Override
    @Transactional
    public UserDTO selfUpdate(UserUpdateRequestDTO dto) {
        User user = getCurrentUser();
        if (user == null) throw new NoPermissionException("Invalid user session");
        this.moveNonNullValuesToEntityFromUpdateDTO(user, dto);
        return mapper.toDTO(user);
    }

    @Override
    @Transactional
    public UserDTO update(Long id, UserUpdateRequestDTO dto) {
        Role currentUserRole = getCurrentUserRole();
        return switch (currentUserRole) {
            case ADMIN -> {
                User user = repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
                this.moveNonNullValuesToEntityFromUpdateDTO(user, dto);
                yield mapper.toDTO(user);
            }
            case null, default -> throw new NoPermissionException();
        };
    }

    private void moveNonNullValuesToEntityFromUpdateDTO(User user, UserUpdateRequestDTO dto) {
        if (dto.username() != null) user.setUsername(dto.username());
        if (dto.password() != null) user.setPassword(dto.password());
        if (dto.fullName() != null) user.setFullName(dto.fullName());
        if (dto.phoneNumber() != null) user.setPhoneNumber(dto.phoneNumber());
    }

    @Override
    @Transactional
    public void softDelete(Long id) {
        Role currentUserRole = getCurrentUserRole();
        switch (currentUserRole) {
            case ADMIN -> {
                User user = repository.findById(id).
                        orElseThrow(() -> new NotFoundException("User not found"));
                user.setDeletionDate(LocalDateTime.now());
            }
            case null, default -> throw new NoPermissionException();
        };
    }

    public static User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String)) {
            Object principal = auth.getPrincipal();
            if (principal instanceof UserDetails) {
                return (User) principal;
            }
        }
        return null;
    }

    public static Role getCurrentUserRole() {
        User user = getCurrentUser();
        return user != null ? user.getRole() : null;
    }

}
