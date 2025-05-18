package task.college.messenger.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import task.college.messenger.dto.MessageDTO;
import task.college.messenger.dto.MessageResponseDTO;
import task.college.messenger.entities.Message;
import task.college.messenger.entities.User;
import task.college.messenger.repos.MessageRepository;
import task.college.messenger.repos.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageResponseDTO createMessage(MessageDTO dto, String senderUsername) {
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Message message = new Message();
        message.setContent(dto.content());
        message.setSender(sender);
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);

        return new MessageResponseDTO(
                message.getContent(),
                message.getSender().getUsername(),
                message.getTimestamp()
        );
    }

    public List<MessageResponseDTO> getAllMessages() {
        return messageRepository.findAllByOrderByTimestampAsc().stream()
                .map(message -> new MessageResponseDTO(
                        message.getContent(),
                        message.getSender().getUsername(),
                        message.getTimestamp()
                ))
                .toList();
    }
}