package kg.edu.alatoo.table_reservations_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tables")
public class TablesController {

    @GetMapping("/all")
    public String getAll() {
        return null;
    }

    //todo
}
