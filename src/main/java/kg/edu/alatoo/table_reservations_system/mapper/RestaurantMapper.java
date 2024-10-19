package kg.edu.alatoo.table_reservations_system.mapper;

import kg.edu.alatoo.table_reservations_system.entity.Restaurant;
import kg.edu.alatoo.table_reservations_system.payload.restaurant.RestaurantCreateRequestDTO;
import kg.edu.alatoo.table_reservations_system.payload.restaurant.RestaurantDTO;
import kg.edu.alatoo.table_reservations_system.payload.restaurant.RestaurantEditRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantDTO toDTO(Restaurant r);
    Restaurant toEntity(RestaurantDTO dto);
    Restaurant toEntity(RestaurantCreateRequestDTO dto);
    Restaurant toEntity(RestaurantEditRequestDTO dto);
}
