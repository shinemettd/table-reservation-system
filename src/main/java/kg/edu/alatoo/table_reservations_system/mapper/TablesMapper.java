package kg.edu.alatoo.table_reservations_system.mapper;

import kg.edu.alatoo.table_reservations_system.entity.TableEntity;
import kg.edu.alatoo.table_reservations_system.payload.table.TableDTO;
import kg.edu.alatoo.table_reservations_system.payload.table.EditTableDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TablesMapper {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "restaurant.name", target = "restaurantName")
    TableDTO toDTO(TableEntity te);

    TableEntity toEntity(TableDTO dto);

    TableEntity toEntity(EditTableDTO dto);

}
