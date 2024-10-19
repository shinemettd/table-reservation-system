package kg.edu.alatoo.table_reservations_system.controller.tables;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.edu.alatoo.table_reservations_system.payload.table.TableDTO;
import kg.edu.alatoo.table_reservations_system.payload.table.CreateRestaurantTablesDTO;
import kg.edu.alatoo.table_reservations_system.payload.table.EditTableDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/tables")
@Tag(name = "Table's API", description = "Operations for managing tables in restaurants")
public sealed interface TablesControllerDocumentation permits TablesController {

    @GetMapping("/all")
    @Operation(summary = "Returns set of tables", description = "Returns all tables depends on user role (if admin, then get even deleted tables)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success get request"),
            @ApiResponse(responseCode = "404", description = "Not found any table")
    })
    ResponseEntity<Set<TableDTO>> getAll();

    @GetMapping("/all-by-restaurant/{restaurantId}")
    @Operation(summary = "Returns set of tables by restaurant id", description = "Returns all tables of restaurant depends on user role (if admin, then get even deleted tables)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success get request"),
            @ApiResponse(responseCode = "404", description = "Not found any table")
    })
    ResponseEntity<Set<TableDTO>> getAllByRestaurantId(
            @Parameter(description = "Id of the restaurant", required = true)
            @PathVariable("restaurantId") Long restaurantId);

    @PostMapping("/create/{restaurantId}")
    @Operation(summary = "Create tables in restaurant by its id", description = "Creating tables with data in dto, validating already existing tables (their numbers) and gotten set of table numbers itself")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success create"),
            @ApiResponse(responseCode = "400", description = "Got same table numbers in DTO or existing table number to create"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    ResponseEntity<Set<TableDTO>> create(
            @Parameter(description = "Id of the restaurant where to create tables", required = true)
            @PathVariable("restaurantId") Long id, @RequestBody CreateRestaurantTablesDTO dto);

    @PutMapping("/edit/id/{id}")
    @Operation(summary = "Edit table by it's id", description = "Edit table by table entity id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success edit"),
            @ApiResponse(responseCode = "400", description = "Got same table numbers in DTO or existing table number to create"),
            @ApiResponse(responseCode = "404", description = "Table not found")
    })
    ResponseEntity<TableDTO> editById(
            @Parameter(description = "Id of table to edit", required = true)
            @PathVariable("id") Long id, @RequestBody EditTableDTO dto);

    @PutMapping("/edit/by-restaurant/{restaurantId}/{tableNumber}")
    @Operation(summary = "Edit table by restaurant's id and number of table", description = "Edit table by restaurant's entity id and existing number of table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success edit"),
            @ApiResponse(responseCode = "400", description = "Table does not exist (deleted)"),
            @ApiResponse(responseCode = "404", description = "Restaurant or table not found")
    })
    ResponseEntity<TableDTO> editByRestaurantIdAndTableNumber(
            @Parameter(description = "Id of restaurant to table edit", required = true)
            @PathVariable("restaurantId") Long restaurantId,
            @Parameter(description = "Number of table to edit", required = true)
            @PathVariable("tableNumber") Long tableNumber, @RequestBody EditTableDTO dto);

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Deletes table by table id", description = "Soft deletes table by table entity id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success delete"),
            @ApiResponse(responseCode = "400", description = "Table does not exist (deleted)"),
            @ApiResponse(responseCode = "404", description = "Table not found")
    })
    ResponseEntity<Void> softDeleteById(@PathVariable("id") Long id);

    @DeleteMapping("/delete/all-by-restaurant/{restaurantId}")
    @Operation(summary = "Deletes all tables by restaurant's id", description = "Soft deletes all tables by restaurant's entity id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success delete"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    ResponseEntity<Void> deleteAllRestaurantTablesByRestaurantId(
            @Parameter(description = "Id of table to delete", required = true)
            @PathVariable("restaurantId") Long restaurantId);

    @DeleteMapping("/delete/by-restaurant/{restaurantId}/{tableNumber}")
    @Operation(summary = "Deletes table by restaurant's id and number of table", description = "Soft deletes table by restaurant's entity id and existing number of table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success delete"),
            @ApiResponse(responseCode = "400", description = "Table does not exist (deleted)"),
            @ApiResponse(responseCode = "404", description = "Restaurant or table number not found")
    })
    ResponseEntity<Void> deleteByRestaurantIdAndTableNumber(
            @Parameter(description = "Id of restaurant to table delete", required = true)
            @PathVariable("restaurantId") Long restaurantId,
            @Parameter(description = "Number of table to delete", required = true)
            @PathVariable("tableNumber") Long tableNumber);
}
