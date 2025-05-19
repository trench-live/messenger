package task.college.messenger.exceptions;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(Long id) {
        super("Message not found with id: " + id);
    }
}