package kg.edu.alatoo.table_reservations_system.service;

import kg.edu.alatoo.table_reservations_system.payload.TableDTO;

import java.util.Set;

public interface TableService {
    Set<TableDTO> getAllAvailable(Long id);
    Set<TableDTO> getAllAvailableByRestaurantId(Long id);
    //todo
}
