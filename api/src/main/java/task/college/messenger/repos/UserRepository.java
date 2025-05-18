package task.college.messenger.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import task.college.messenger.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}