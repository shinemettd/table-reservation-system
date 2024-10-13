package kg.edu.alatoo.table_reservations_system.controller;

import kg.edu.alatoo.table_reservations_system.payload.RestaurantDTO;
import kg.edu.alatoo.table_reservations_system.service.RestaurantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestaurantController {

    RestaurantService service;
    //todo
    ResponseEntity<?> edit(@RequestBody RestaurantDTO dto) {
        return ResponseEntity.ok(service.edit(dto));
    }
}
