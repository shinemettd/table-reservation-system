package kg.edu.alatoo.table_reservations_system.controller.restaurant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.edu.alatoo.table_reservations_system.payload.RestaurantDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/restaurant")
@Tag(name = "Restaurant's API", description = "Operations for managing restaurants")
public sealed interface RestaurantControllerDocumentation permits RestaurantController {

    @GetMapping("/all")
    @Operation(summary = "Get all restaurants", description = "Returns all restaurants as DTO for admin, and non-deleted for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success get"),
            @ApiResponse(responseCode = "404", description = "Restaurants not found")
    })
    ResponseEntity<Set<RestaurantDTO>> getAll();

    @GetMapping("/{id}")
    @Operation(summary = "Get restaurant by ID", description = "Returns a restaurant by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success get"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    ResponseEntity<RestaurantDTO> getById(
            @Parameter(description = "Unique identifier of the restaurant", required = true)
            @PathVariable("id") Long id);

    @GetMapping("/{name}")
    @Operation(summary = "Get restaurants by exact name", description = "Returns a set of restaurants by their name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success get"),
            @ApiResponse(responseCode = "404", description = "Restaurants not found")
    })
    ResponseEntity<Set<RestaurantDTO>> getByName(
            @Parameter(description = "Name of the restaurant", required = true)
            @PathVariable("name") String name);

    @GetMapping("/search")
    @Operation(summary = "Search restaurants by similar name", description = "Searches restaurants by name substring")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success search"),
            @ApiResponse(responseCode = "404", description = "No restaurants found for the given name")
    })
    ResponseEntity<Set<RestaurantDTO>> searchByName(
            @Parameter(description = "Name of the restaurant to search", required = true)
            @RequestParam String name);

    @PostMapping("/create")
    @Operation(summary = "Create a new restaurant", description = "Creates a new restaurant in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant created"),
            @ApiResponse(responseCode = "403", description = "No permission for this action")
    })
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<RestaurantDTO> create(@RequestBody RestaurantDTO dto);

    @PutMapping("/edit/{id}")
    @Operation(summary = "Edit an existing restaurant", description = "Edits the details of an existing restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success edit"),
            @ApiResponse(responseCode = "403", description = "No permission for edit"),
            @ApiResponse(responseCode = "404", description = "Restaurant for edit not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<RestaurantDTO> edit(
            @Parameter(description = "ID of the restaurant to edit", required = true)
            @PathVariable("id") Long id, @RequestBody RestaurantDTO dto);


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a restaurant with all tables", description = "Soft deletes a restaurant and all its tables by its ID and")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success delete"),
            @ApiResponse(responseCode = "403", description = "No permission for delete"),
            @ApiResponse(responseCode = "404", description = "Restaurant for delete not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> softDeleteById(
            @Parameter(description = "ID of the restaurant to delete", required = true)
            @PathVariable("id") Long id);
}
