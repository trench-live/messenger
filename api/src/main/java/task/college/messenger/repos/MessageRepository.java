package task.college.messenger.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import task.college.messenger.entities.Message;
import task.college.messenger.entities.User;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderOrderByTimestampDesc(User sender);
    List<Message> findByRecipientOrderByTimestampDesc(User recipient);
    List<Message> findBySenderOrRecipientOrderByTimestampDesc(User sender, User recipient);
}