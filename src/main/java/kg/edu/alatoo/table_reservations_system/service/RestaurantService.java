package kg.edu.alatoo.table_reservations_system.service;

import kg.edu.alatoo.table_reservations_system.payload.RestaurantDTO;

import java.util.Set;

public interface RestaurantService {
    Set<RestaurantDTO> getAll();
    RestaurantDTO getById(Long id);
    Set<RestaurantDTO> searchByName(String name);
    RestaurantDTO getByName(String name);
    RestaurantDTO create(RestaurantDTO dto);
    RestaurantDTO edit(RestaurantDTO dto);
    void softDeleteById(Long id);
}
