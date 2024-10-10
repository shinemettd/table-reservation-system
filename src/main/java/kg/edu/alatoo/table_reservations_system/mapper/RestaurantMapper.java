package kg.edu.alatoo.table_reservations_system.mapper;

import kg.edu.alatoo.table_reservations_system.entity.Restaurant;
import kg.edu.alatoo.table_reservations_system.payload.RestaurantDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantDTO toDto(Restaurant r);
    Restaurant toEntity(RestaurantDTO dto);
}
