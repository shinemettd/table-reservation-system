package kg.edu.alatoo.table_reservations_system.service;

import kg.edu.alatoo.table_reservations_system.payload.TableDTO;
import kg.edu.alatoo.table_reservations_system.payload.table.CreateRestaurantTablesDTO;
import kg.edu.alatoo.table_reservations_system.payload.table.RestaurantTableDTO;

import java.util.Set;

public interface TableService {
    Set<TableDTO> getAll();
    Set<TableDTO> getAllNonDeleted();
    Set<TableDTO> getAllByRestaurantId(Long restaurantId);
    Set<TableDTO> getAllByRestaurantIdNonDeleted(Long restaurantId);
    Set<TableDTO> create(CreateRestaurantTablesDTO dto);
    TableDTO editById(Long id);
    TableDTO editByRestaurantTableDTO(RestaurantTableDTO dto);
    void softDeleteById(Long id);
    void deleteAllRestaurantTablesByItsId(Long restaurantId);
    void deleteByRestaurantTableDTO(RestaurantTableDTO dto);
}
