package com.example.backend.model.respon;

import java.time.LocalDateTime;
import com.example.backend.model.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private Long id;
    private String message;
    private NotificationType type;
    private Boolean isRead;
    private String action;
    private UserResponse user;
    private Long targetId;
    private LocalDateTime createdAt;
}
