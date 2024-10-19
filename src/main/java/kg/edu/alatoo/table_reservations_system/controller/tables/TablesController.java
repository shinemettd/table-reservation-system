package kg.edu.alatoo.table_reservations_system.controller.tables;

import kg.edu.alatoo.table_reservations_system.payload.table.TableDTO;
import kg.edu.alatoo.table_reservations_system.payload.table.CreateRestaurantTablesDTO;
import kg.edu.alatoo.table_reservations_system.payload.table.EditTableDTO;
import kg.edu.alatoo.table_reservations_system.service.TableService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class TablesController implements TablesControllerDocumentation {

    TableService service;

    @Override
    public ResponseEntity<Set<TableDTO>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Set<TableDTO>> getAllByRestaurantId(Long restaurantId) {
        return new ResponseEntity<>(service.getAllByRestaurantId(restaurantId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Set<TableDTO>> create(Long restaurantId, CreateRestaurantTablesDTO dto) {
        return new ResponseEntity<>(service.create(restaurantId, dto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TableDTO> editById(Long id, EditTableDTO dto) {
        return new ResponseEntity<>(service.editById(id, dto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TableDTO> editByRestaurantIdAndTableNumber(Long restaurantId, Long tableNumber, EditTableDTO dto) {
        return new ResponseEntity<>(service.editByRestaurantIdAndTableNumber(restaurantId, tableNumber, dto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> softDeleteById(Long id) {
        service.softDeleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteAllRestaurantTablesByRestaurantId(Long restaurantId) {
        service.deleteAllRestaurantTablesByRestaurantId(restaurantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteByRestaurantIdAndTableNumber(Long restaurantId, Long tableNumber) {
        service.deleteByRestaurantIdAndTableNumber(restaurantId, tableNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
