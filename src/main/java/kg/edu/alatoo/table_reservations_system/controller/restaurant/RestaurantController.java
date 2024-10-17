package kg.edu.alatoo.table_reservations_system.controller.restaurant;

import kg.edu.alatoo.table_reservations_system.payload.RestaurantDTO;
import kg.edu.alatoo.table_reservations_system.service.RestaurantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        return ResponseEntity.ok(service.getByName(name));
    }

    @Override
    public ResponseEntity<Set<RestaurantDTO>> searchByName(String name) {
        return ResponseEntity.ok(service.searchByName(name));
    }

    @Override
    public ResponseEntity<RestaurantDTO> create(RestaurantDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Override
    public ResponseEntity<RestaurantDTO> edit(Long id, RestaurantDTO dto) {
        return ResponseEntity.ok(service.edit(dto));
    }

    @Override
    public ResponseEntity<Void> softDeleteById(Long id) {
        service.softDeleteById(id);
        return ResponseEntity.ok().build();
    }

}
