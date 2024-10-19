package kg.edu.alatoo.table_reservations_system.service;

import kg.edu.alatoo.table_reservations_system.payload.table.TableDTO;
import kg.edu.alatoo.table_reservations_system.payload.table.CreateRestaurantTablesDTO;
import kg.edu.alatoo.table_reservations_system.payload.table.EditTableDTO;

import java.util.Set;

public interface TableService {
    Set<TableDTO> getAll();
    Set<TableDTO> getAllByRestaurantId(Long restaurantId);
    Set<TableDTO> create(Long id, CreateRestaurantTablesDTO dto);
    TableDTO editById(Long id, EditTableDTO dto);
    TableDTO editByRestaurantIdAndTableNumber(Long restaurantId, Long tableNumber, EditTableDTO dto);
    void softDeleteById(Long id);
    void deleteAllRestaurantTablesByRestaurantId(Long restaurantId);
    void deleteByRestaurantIdAndTableNumber(Long restaurantId, Long tableNumber);
}
