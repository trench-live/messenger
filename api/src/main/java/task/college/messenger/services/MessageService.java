package task.college.messenger.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import task.college.messenger.dto.request.CreateMessageRequest;
import task.college.messenger.dto.response.MessageResponse;
import task.college.messenger.entities.Message;
import task.college.messenger.entities.User;
import task.college.messenger.exceptions.UserNotFoundException;
import task.college.messenger.mappers.MessageMapper;
import task.college.messenger.repos.MessageRepository;
import task.college.messenger.repos.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;

    public MessageResponse createMessage(CreateMessageRequest request, String senderUsername) {
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        User recipient = request.recipientId() != null
                ? userRepository.findById(request.recipientId())
                .orElseThrow(() -> new UserNotFoundException("Recipient not found"))
                : null;

        Message message = new Message();
        message.setContent(request.content());
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setTimestamp(LocalDateTime.now());

        return messageMapper.toResponse(messageRepository.save(message));
    }

    public List<MessageResponse> getUserMessages(String username, String type) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Message> messages = switch (type) {
            case "sent" -> messageRepository.findBySenderOrderByTimestampDesc(user);
            case "inbox" -> messageRepository.findByRecipientOrderByTimestampDesc(user);
            default -> messageRepository.findBySenderOrRecipientOrderByTimestampDesc(user, user);
        };

        return messages.stream()
                .map(messageMapper::toResponse)
                .toList();
    }
}