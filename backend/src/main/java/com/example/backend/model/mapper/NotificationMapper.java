package com.example.backend.model.mapper;

import com.example.backend.model.entity.Notification;
import com.example.backend.model.entity.User;
import com.example.backend.model.request.NotificationRequest;
import com.example.backend.model.respon.NotificationResponse;
import com.example.backend.model.respon.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationMapper {

    private final UserMapper userMapper;

    public Notification toEntity(NotificationRequest request) {
        if (request == null) {
            return null;
        }
        return Notification.builder()
                .message(request.getMessage())
                .type(request.getType())
                .isRead(request.getIsRead())
                .action(request.getAction())
                .user(request.getUserId() != null ? User.builder().id(request.getUserId()).build() : null)
                .targetId(request.getTargetId())
                .build();
    }

    public NotificationResponse toResponse(Notification entity) {
        if (entity == null) {
            return null;
        }
        return NotificationResponse.builder()
                .id(entity.getId())
                .message(entity.getMessage())
                .type(entity.getType())
                .isRead(entity.getIsRead())
                .action(entity.getAction())
                .user(userMapper.toResponse(entity.getUser()))
                .targetId(entity.getTargetId())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public NotificationRequest toRequest(Notification entity) {
        if (entity == null) {
            return null;
        }
        return NotificationRequest.builder()
                .message(entity.getMessage())
                .type(entity.getType())
                .isRead(entity.getIsRead())
                .action(entity.getAction())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .targetId(entity.getTargetId())
                .build();
    }

    public Notification toEntity(NotificationResponse response) {
        if (response == null) {
            return null;
        }
        return Notification.builder()
                .id(response.getId())
                .message(response.getMessage())
                .type(response.getType())
                .isRead(response.getIsRead())
                .action(response.getAction())
                .user(userMapper.toEntity(response.getUser()))
                .targetId(response.getTargetId())
                .createdAt(response.getCreatedAt())
                .build();
    }

    public NotificationResponse toResponse(NotificationRequest request) {
        if (request == null) {
            return null;
        }
        return NotificationResponse.builder()
                .message(request.getMessage())
                .type(request.getType())
                .isRead(request.getIsRead())
                .action(request.getAction())
                .user(request.getUserId() != null ? UserResponse.builder().id(request.getUserId()).build() : null)
                .targetId(request.getTargetId())
                .build();
    }
}
