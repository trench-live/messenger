package task.college.messenger.dto;

import java.time.LocalDateTime;

public record MessageResponseDTO(String content, String sender, LocalDateTime timestamp) {}
