package task.college.messenger.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import task.college.messenger.dto.request.CreateMessageRequest;
import task.college.messenger.dto.response.MessageResponse;
import task.college.messenger.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public MessageResponse sendMessage(
            @RequestBody CreateMessageRequest request,
            Authentication auth) {
        return messageService.createMessage(request, auth.getName());
    }

    @GetMapping
    public List<MessageResponse> getMessages(
            @RequestParam(required = false) String type,
            Authentication auth) {
        return messageService.getUserMessages(auth.getName(), type);
    }
}