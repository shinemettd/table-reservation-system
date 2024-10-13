package kg.edu.alatoo.table_reservations_system.repository;

import kg.edu.alatoo.table_reservations_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByUsername(String username);

    boolean existsUserByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u WHERE u.username = :login OR u.phoneNumber = :login")
    Optional<User> findByLogin(@Param("login") String login);
}
