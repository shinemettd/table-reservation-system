package kg.edu.alatoo.table_reservations_system.repository;

import kg.edu.alatoo.table_reservations_system.entity.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Long> {

    @Query("SELECT DISTINCT t FROM TableEntity t")
    Optional<Set<TableEntity>> findAllTables();

    @Query("SELECT DISTINCT t FROM TableEntity t WHERE t.deletionDate IS NULL")
    Optional<Set<TableEntity>> findAllTablesNonDeleted();

    @Query("SELECT DISTINCT t FROM TableEntity t WHERE t.restaurant.id = :restaurantId")
    Optional<Set<TableEntity>> findAllByRestaurantId(@Param("restaurantId") Long id);

    @Query("SELECT DISTINCT t FROM TableEntity t WHERE t.deletionDate IS NULL AND t.restaurant.id = :restaurantId")
    Optional<Set<TableEntity>> findAllByRestaurantIdNonDeleted(@Param("restaurantId") Long id);

    @Query("SELECT t FROM TableEntity t WHERE t.restaurant.id = :restaurantId AND t.number = :tableNumber")
    Optional<TableEntity> findByRestaurantIdAndTableNumber(@Param("restaurantId") Long restaurantId, @Param("tableNumber") Long tableNumber);

    @Query("SELECT t FROM TableEntity t WHERE t.restaurant.id = :restaurantId AND t.number = :tableNumber AND t.deletionDate IS NULL")
    Optional<TableEntity> findByRestaurantIdAndTableNumberNonDeleted(@Param("restaurantId") Long restaurantId, @Param("tableNumber") Long tableNumber);

}
