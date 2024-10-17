package kg.edu.alatoo.table_reservations_system.service.impl;

import kg.edu.alatoo.table_reservations_system.entity.Restaurant;
import kg.edu.alatoo.table_reservations_system.enums.Role;
import kg.edu.alatoo.table_reservations_system.exceptions.NoPermissionException;
import kg.edu.alatoo.table_reservations_system.exceptions.NotFoundException;
import kg.edu.alatoo.table_reservations_system.mapper.RestaurantMapper;
import kg.edu.alatoo.table_reservations_system.payload.RestaurantDTO;
import kg.edu.alatoo.table_reservations_system.repository.RestaurantRepository;
import kg.edu.alatoo.table_reservations_system.service.RestaurantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static kg.edu.alatoo.table_reservations_system.service.impl.UserServiceImpl.getCurrentUserRole;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestaurantServiceImpl implements RestaurantService {

    RestaurantRepository repository;

    RestaurantMapper mapper;

    @Override
    public Set<RestaurantDTO> getAll() {
        Role currentUserRole = getCurrentUserRole();
        return switch(currentUserRole) {
            case ADMIN -> repository.findAllRestaurants().orElseThrow(NotFoundException::new)
                    .stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toSet());
            case null, default -> repository.findAllRestaurantsNotDeleted().orElseThrow(NotFoundException::new)
                    .stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toSet());
        };
    }

    @Override
    public RestaurantDTO getById(Long id) {
        Role currentUserRole = getCurrentUserRole();
        return switch(currentUserRole)  {
            case ADMIN -> mapper.toDto(repository.findById(id).orElseThrow(NotFoundException::new));
            case null, default -> mapper.toDto(repository.findByIdAndDeletionDateIsNull(id).orElseThrow(NotFoundException::new));
        };
    }

    public Restaurant getEntityById(Long id) {
        Role currentUserRole = getCurrentUserRole();
        return switch(currentUserRole)  {
            case ADMIN -> repository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant not found"));
            case null, default -> repository.findByIdAndDeletionDateIsNull(id).orElseThrow(() -> new NotFoundException("Restaurant not found"));
        };
    }

    @Override
    public Set<RestaurantDTO> searchByName(String name) {
        Role currentUserRole = getCurrentUserRole();
        return switch (currentUserRole) {
            case ADMIN -> repository.findAllByLikeName(name).orElseThrow(NotFoundException::new)
                    .stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toSet());
            case null, default -> repository.findAllByLikeNameNotDeleted(name).orElseThrow(NotFoundException::new)
                    .stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toSet());
        };
    }

    @Override
    public Set<RestaurantDTO> getByName(String name) {
        Role currentUserRole = getCurrentUserRole();
        return switch (currentUserRole) {
            case ADMIN -> repository.findAllByName(name).orElseThrow(NotFoundException::new)
                    .stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toSet());
            case null, default -> repository.findAllByNameAndDeletionDateIsNull(name).orElseThrow(NotFoundException::new)
                    .stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toSet());
        };
    }

    @Override
    public RestaurantDTO create(RestaurantDTO dto) {
        Role currentUserRole = getCurrentUserRole();
        return switch (currentUserRole) {
            case ADMIN -> mapper.toDto(repository.save(mapper.toEntity(dto)));
            case null, default -> throw new NoPermissionException();
        };
    }

    @Override
    @Transactional
    public RestaurantDTO edit(RestaurantDTO dto) {
        Role currentUserRole = getCurrentUserRole();
        return switch (currentUserRole) {
            case ADMIN -> {
                var restaurant = repository.findById(dto.id()).orElseThrow(NotFoundException::new);
                restaurant = mapper.toEntity(dto);
                yield mapper.toDto(restaurant);
            }
            case null, default -> throw new NoPermissionException();
        };
    }

    @Override
    @Transactional
    public void softDeleteById(Long id) {
        Role currentUserRole = getCurrentUserRole();
        var deleteDate = LocalDateTime.now();
        switch (currentUserRole) {
            case ADMIN -> {
                var restaurant = repository.findById(id).orElseThrow(NotFoundException::new);
                restaurant.setDeletionDate(deleteDate);
                restaurant.getTables().forEach(e -> e.setDeletionDate(deleteDate));
            }
            case null, default -> throw new NoPermissionException();
        };
    }

}
