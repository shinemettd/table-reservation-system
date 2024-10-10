package kg.edu.alatoo.table_reservations_system.mapper;

import kg.edu.alatoo.table_reservations_system.entity.TableEntity;
import kg.edu.alatoo.table_reservations_system.payload.TableDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TablesMapper {
    TableDTO toDTO(TableEntity te);
    TableEntity toEntity(TableDTO dto);
}
