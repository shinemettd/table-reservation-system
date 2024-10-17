package kg.edu.alatoo.table_reservations_system.service.impl;

import kg.edu.alatoo.table_reservations_system.entity.TableEntity;
import kg.edu.alatoo.table_reservations_system.exceptions.TableAlreadyExists;
import kg.edu.alatoo.table_reservations_system.payload.TableDTO;
import kg.edu.alatoo.table_reservations_system.payload.table.CreateRestaurantTablesDTO;
import kg.edu.alatoo.table_reservations_system.payload.table.RestaurantTableDTO;
import kg.edu.alatoo.table_reservations_system.payload.table.TablePropertiesDTO;
import kg.edu.alatoo.table_reservations_system.repository.TableRepository;
import kg.edu.alatoo.table_reservations_system.service.RestaurantService;
import kg.edu.alatoo.table_reservations_system.service.TableService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TableServiceImpl implements TableService {

    RestaurantService restaurantService;

    TableRepository tableRepository;

    @Override
    public Set<TableDTO> getAll() {
        return Set.of();
    }

    @Override
    public Set<TableDTO> getAllNonDeleted() {
        return Set.of();
    }

    @Override
    public Set<TableDTO> getAllByRestaurantId(Long restaurantId) {
        return Set.of();
    }

    @Override
    public Set<TableDTO> getAllByRestaurantIdNonDeleted(Long restaurantId) {
        return Set.of();
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
    public Set<TableDTO> create(CreateRestaurantTablesDTO dto) {
        var restaurant = restaurantService.getEntityById(dto.restaurantId());

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
            throw new IllegalArgumentException(numbers.toString()); //todo custom exception
        }

        Set<Long> matchingTableNumbers = restaurant.getTables().stream()
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

        tableRepository.saveAll(tablesToSave);
        //todo add mapper
        return null;
    }

    @Override
    public TableDTO editById(Long id) {
        return null;
    }

    @Override
    public TableDTO editByRestaurantTableDTO(RestaurantTableDTO dto) {
        return null;
    }

    @Override
    public void softDeleteById(Long id) {

    }

    @Override
    public void deleteAllRestaurantTablesByItsId(Long restaurantId) {

    }

    @Override
    public void deleteByRestaurantTableDTO(RestaurantTableDTO dto) {

    }
}
