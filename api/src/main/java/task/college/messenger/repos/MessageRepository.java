package task.college.messenger.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import task.college.messenger.entities.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByOrderByTimestampAsc();
}