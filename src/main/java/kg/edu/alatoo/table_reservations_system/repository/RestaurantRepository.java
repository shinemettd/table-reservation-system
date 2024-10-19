package kg.edu.alatoo.table_reservations_system.repository;

import kg.edu.alatoo.table_reservations_system.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT DISTINCT r FROM Restaurant r")
    Optional<Set<Restaurant>> findAllRestaurantsWithAllTables();

    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "WHERE r.deletionDate IS NULL")
    Optional<Set<Restaurant>> findAllRestaurantsNotDeleted();

    @Query("SELECT r FROM Restaurant r WHERE r.id = :id")
    Optional<Restaurant> findById(@Param("id") Long id);

    Optional<Restaurant> findByIdAndDeletionDateIsNull(Long id);

    @Query("SELECT DISTINCT r FROM Restaurant r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Optional<Set<Restaurant>> findAllByLikeName(@Param("name") String name);

    @Query("SELECT DISTINCT r FROM Restaurant r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%')) AND r.deletionDate IS NULL")
    Optional<Set<Restaurant>> findAllByLikeNameNotDeleted(@Param("name") String name);

    Optional<Set<Restaurant>> findAllByName(String name);

    Optional<Set<Restaurant>> findAllByNameAndDeletionDateIsNull(String name);

}
