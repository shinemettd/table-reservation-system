package kg.edu.alatoo.table_reservations_system.service.impl;

import kg.edu.alatoo.table_reservations_system.entity.TableEntity;
import kg.edu.alatoo.table_reservations_system.enums.Role;
import kg.edu.alatoo.table_reservations_system.exceptions.*;
import kg.edu.alatoo.table_reservations_system.mapper.TablesMapper;
import kg.edu.alatoo.table_reservations_system.payload.table.TableDTO;
import kg.edu.alatoo.table_reservations_system.payload.table.CreateRestaurantTablesDTO;
import kg.edu.alatoo.table_reservations_system.payload.table.EditTableDTO;
import kg.edu.alatoo.table_reservations_system.payload.table.TablePropertiesDTO;
import kg.edu.alatoo.table_reservations_system.repository.TableRepository;
import kg.edu.alatoo.table_reservations_system.service.RestaurantService;
import kg.edu.alatoo.table_reservations_system.service.TableService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static kg.edu.alatoo.table_reservations_system.service.impl.UserServiceImpl.getCurrentUserRole;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TableServiceImpl implements TableService {

    RestaurantService restaurantService;

    TableRepository repository;

    TablesMapper mapper;

    @Override
    public Set<TableDTO> getAll() {
        Role currentUserRole = getCurrentUserRole();
        return switch (currentUserRole) {
            case ADMIN -> repository.findAllTables().orElseThrow(NotFoundException::new)
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toSet());
            case null, default -> repository.findAllTablesNonDeleted().orElseThrow(NotFoundException::new)
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toSet());
        };
    }

    @Override
    public Set<TableDTO> getAllByRestaurantId(Long restaurantId) {
        Role currentUserRole = getCurrentUserRole();
        return switch (currentUserRole) {
            case ADMIN -> repository.findAllByRestaurantId(restaurantId).orElseThrow(NotFoundException::new)
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toSet());
            case null, default -> repository.findAllByRestaurantIdNonDeleted(restaurantId).orElseThrow(NotFoundException::new)
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toSet());
        };
    }

    /**
     * First, search for a restaurant using the specified ID.
     * Then, validate the set created from the combination of table number and capacity.
     * If there are duplicate table numbers, identify the repeated numbers and raise an exception.
     * If no issues are found, we check for any matching table numbers.
     * If matches are found, an exception is returned with the list of those numbers.
     * After successful validation, we proceed to gather the data in list for saving the tables.
     *
     */
    @Override
    @Transactional
    public Set<TableDTO> create(Long restaurantId, CreateRestaurantTablesDTO dto) {
        var restaurant = restaurantService.getEntityById(restaurantId);

        long uniqueTableNumbersCount = dto.tables().stream()
                .map(TablePropertiesDTO::number)
                .distinct()
                .count();

        if (uniqueTableNumbersCount != dto.tables().size()) {
            Set<Long> numbers = dto.tables().stream()
                    .map(TablePropertiesDTO::number)
                    .collect(Collectors.groupingBy(number -> number, Collectors.counting()))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
            throw new NotValidException(String.format("Got same table numbers: %s", numbers));
        }

        Set<Long> matchingTableNumbers = restaurant.getTables().stream()
                .filter(table -> table.getDeletionDate() == null)
                .map(TableEntity::getNumber)
                .filter(number -> dto.tables().stream()
                                     .map(TablePropertiesDTO::number)
                                     .anyMatch(dtoNumber -> dtoNumber.equals(number)))
                .collect(Collectors.toSet());

        if (!matchingTableNumbers.isEmpty()) {
            throw new TableAlreadyExists(String.format("Table %s already exist in that restaurant!", matchingTableNumbers));
        }

        List<TableEntity> tablesToSave = dto.tables().stream()
                .map(tables -> new TableEntity(restaurant, tables.number(), tables.capacity()))
                .toList();

        restaurant.getTables().addAll(tablesToSave);

        return repository.saveAll(tablesToSave).stream().
                map(mapper::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public TableDTO editById(Long id, EditTableDTO dto) {
        Role currentUserRole = getCurrentUserRole();
        return switch (currentUserRole) {
            case ADMIN -> {
                var table = repository.findById(id).orElseThrow(NotFoundException::new);
                if (table.getDeletionDate() == null) throw new DeletedEntityException();
                table.setNumber(dto.number());
                table.setCapacity(dto.capacity());
                yield mapper.toDTO(table);
            }
            case null, default -> throw new NoPermissionException();
        };
    }

    @Override
    @Transactional
    public TableDTO editByRestaurantIdAndTableNumber(Long restaurantId, Long tableNumber, EditTableDTO dto) {
        Role currentUserRole = getCurrentUserRole();
        return switch (currentUserRole) {
            case ADMIN -> {
                var table = repository.findByRestaurantIdAndTableNumber(restaurantId, tableNumber)
                        .orElseThrow(NotFoundException::new);
                if (table.getDeletionDate() == null) throw new DeletedEntityException();
                table.setNumber(dto.number());
                table.setCapacity(dto.capacity());
                yield mapper.toDTO(table);
            }
            case null, default -> throw new NoPermissionException();
        };
    }

    @Override
    @Transactional
    public void softDeleteById(Long id) {
        Role currentUserRole = getCurrentUserRole();
        switch (currentUserRole) {
            case ADMIN -> {
                var table = repository.findById(id)
                        .orElseThrow(NotFoundException::new);
                if (table.getDeletionDate() == null) throw new DeletedEntityException();
                table.setDeletionDate(LocalDateTime.now());
            }
            case null, default -> throw new NoPermissionException();
        };
    }

    @Override
    @Transactional
    public void deleteAllRestaurantTablesByRestaurantId(Long restaurantId) {
        Role currentUserRole = getCurrentUserRole();
        LocalDateTime deletionDate = LocalDateTime.now();
        switch (currentUserRole) {
            case ADMIN -> {
                restaurantService.getEntityById(restaurantId).getTables()
                        .forEach(e -> e.setDeletionDate(deletionDate));
            }
            case null, default -> throw new NoPermissionException();
        };
    }

    @Override
    @Transactional
    public void deleteByRestaurantIdAndTableNumber(Long restaurantId, Long tableNumber) {
        Role currentUserRole = getCurrentUserRole();
        switch (currentUserRole) {
            case ADMIN -> {
                var table = repository.findByRestaurantIdAndTableNumber(restaurantId, tableNumber)
                        .orElseThrow(NotFoundException::new);
                if (table.getDeletionDate() == null) throw new DeletedEntityException();
                table.setDeletionDate(LocalDateTime.now());
            }
            case null, default -> throw new NoPermissionException();
        };
    }
}
