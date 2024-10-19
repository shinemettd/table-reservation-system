package kg.edu.alatoo.table_reservations_system.controller.restaurant;

import kg.edu.alatoo.table_reservations_system.payload.restaurant.RestaurantCreateRequestDTO;
import kg.edu.alatoo.table_reservations_system.payload.restaurant.RestaurantDTO;
import kg.edu.alatoo.table_reservations_system.payload.restaurant.RestaurantEditRequestDTO;
import kg.edu.alatoo.table_reservations_system.service.RestaurantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class RestaurantController implements RestaurantControllerDocumentation {

    RestaurantService service;

    @Override
    public ResponseEntity<Set<RestaurantDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    public ResponseEntity<RestaurantDTO> getById(Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Override
    public ResponseEntity<Set<RestaurantDTO>> getByName(String name) {
        return new ResponseEntity<>(service.getByName(name), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Set<RestaurantDTO>> searchByName(String name) {
        return new ResponseEntity<>(service.searchByName(name), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RestaurantDTO> create(RestaurantCreateRequestDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RestaurantDTO> edit(Long id, RestaurantEditRequestDTO dto) {
        return new ResponseEntity<>(service.edit(id, dto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> softDeleteById(Long id) {
        service.softDeleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
