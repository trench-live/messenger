package task.college.messenger.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import task.college.messenger.dto.MessageDTO;
import task.college.messenger.dto.MessageResponseDTO;
import task.college.messenger.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public MessageResponseDTO sendMessage(@RequestBody MessageDTO dto, Authentication auth) {
        return messageService.createMessage(dto, auth.getName());
    }

    @GetMapping
    public List<MessageResponseDTO> getMessages() {
        return messageService.getAllMessages();
    }
}