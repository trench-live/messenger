package task.college.messenger.mappers;

import org.springframework.stereotype.Component;
import task.college.messenger.dto.response.MessageResponse;
import task.college.messenger.entities.Message;

@Component
public class MessageMapper {
    public MessageResponse toResponse(Message message) {
        return new MessageResponse(
                message.getId(),
                message.getContent(),
                message.getSender().getUsername(),
                message.getRecipient() != null ? message.getRecipient().getUsername() : null,
                message.getTimestamp()
        );
    }
}