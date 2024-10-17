package kg.edu.alatoo.table_reservations_system.service;

import kg.edu.alatoo.table_reservations_system.entity.Restaurant;
import kg.edu.alatoo.table_reservations_system.payload.RestaurantDTO;

import java.util.Set;

public interface RestaurantService {
    Set<RestaurantDTO> getAll();
    RestaurantDTO getById(Long id);
    Restaurant getEntityById(Long id);
    Set<RestaurantDTO> getByName(String name);
    Set<RestaurantDTO> searchByName(String name);
    RestaurantDTO create(RestaurantDTO dto);
    RestaurantDTO edit(RestaurantDTO dto);
    void softDeleteById(Long id);
}
