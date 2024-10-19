package kg.edu.alatoo.table_reservations_system.service.impl;

import kg.edu.alatoo.table_reservations_system.entity.Restaurant;
import kg.edu.alatoo.table_reservations_system.enums.Role;
import kg.edu.alatoo.table_reservations_system.exceptions.DeletedEntityException;
import kg.edu.alatoo.table_reservations_system.exceptions.NoPermissionException;
import kg.edu.alatoo.table_reservations_system.exceptions.NotFoundException;
import kg.edu.alatoo.table_reservations_system.mapper.RestaurantMapper;
import kg.edu.alatoo.table_reservations_system.payload.restaurant.RestaurantCreateRequestDTO;
import kg.edu.alatoo.table_reservations_system.payload.restaurant.RestaurantDTO;
import kg.edu.alatoo.table_reservations_system.payload.restaurant.RestaurantEditRequestDTO;
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
            case ADMIN -> repository.findAllRestaurantsWithAllTables().orElseThrow(NotFoundException::new)
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toSet());
            case null, default -> repository.findAllRestaurantsNotDeleted().orElseThrow(NotFoundException::new)
                    .stream()
                    .peek(r -> r.setTables(r.getTables().stream()
                            .filter(t -> t.getDeletionDate() == null)
                            .collect(Collectors.toSet())))
                    .map(mapper::toDTO)
                    .collect(Collectors.toSet());
        };
    }

    @Override
    public RestaurantDTO getById(Long id) {
        Role currentUserRole = getCurrentUserRole();
        return switch(currentUserRole)  {
            case ADMIN -> mapper.toDTO(repository.findById(id).orElseThrow(NotFoundException::new));
            case null, default -> {
                var restaurant = repository.findByIdAndDeletionDateIsNull(id).orElseThrow(NotFoundException::new);
                restaurant.setTables(restaurant.getTables().stream()
                        .filter(table -> table.getDeletionDate() == null)
                        .collect(Collectors.toSet()));
                yield mapper.toDTO(restaurant);
            }
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
                    .map(mapper::toDTO)
                    .collect(Collectors.toSet());
            case null, default -> repository.findAllByLikeNameNotDeleted(name).orElseThrow(NotFoundException::new)
                    .stream()
                    .peek(r -> r.setTables(r.getTables().stream()
                            .filter(t -> t.getDeletionDate() == null)
                            .collect(Collectors.toSet())))
                    .map(mapper::toDTO)
                    .collect(Collectors.toSet());
        };
    }

    @Override
    public Set<RestaurantDTO> getByName(String name) {
        Role currentUserRole = getCurrentUserRole();
        return switch (currentUserRole) {
            case ADMIN -> repository.findAllByName(name).orElseThrow(NotFoundException::new)
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toSet());
            case null, default -> repository.findAllByNameAndDeletionDateIsNull(name).orElseThrow(NotFoundException::new)
                    .stream()
                    .peek(r -> r.setTables(r.getTables().stream()
                            .filter(t -> t.getDeletionDate() == null)
                            .collect(Collectors.toSet())))
                    .map(mapper::toDTO)
                    .collect(Collectors.toSet());
        };
    }

    @Override
    public RestaurantDTO create(RestaurantCreateRequestDTO dto) {
        Role currentUserRole = getCurrentUserRole();
        return switch (currentUserRole) {
            case ADMIN -> mapper.toDTO(repository.save(mapper.toEntity(dto)));
            case null, default -> throw new NoPermissionException();
        };
    }

    @Override
    @Transactional
    public RestaurantDTO edit(Long id, RestaurantEditRequestDTO dto) {
        Role currentUserRole = getCurrentUserRole();
        return switch (currentUserRole) {
            case ADMIN -> {
                var restaurant = repository.findById(id).orElseThrow(NotFoundException::new);
                if (restaurant.getDeletionDate() == null) throw new DeletedEntityException();
                restaurant.setName(dto.name());
                yield mapper.toDTO(restaurant);
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
                if (restaurant.getDeletionDate() == null) throw new DeletedEntityException();
                restaurant.setDeletionDate(deleteDate);
                restaurant.getTables().forEach(e -> e.setDeletionDate(deleteDate));
            }
            case null, default -> throw new NoPermissionException();
        };
    }

}
