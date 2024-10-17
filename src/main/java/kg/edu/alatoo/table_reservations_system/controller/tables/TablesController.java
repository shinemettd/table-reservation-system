package kg.edu.alatoo.table_reservations_system.controller.tables;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public final class TablesController implements TablesControllerDocumentation {

    @GetMapping("/all")
    public String getAll() {
        return null;
    }

    //todo
}
