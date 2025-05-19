package task.college.messenger.dto.response;

import java.time.LocalDateTime;

public record MessageResponse(
        Long id,
        String content,
        String sender,
        String recipient,
        LocalDateTime timestamp
) {}