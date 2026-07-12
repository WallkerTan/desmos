package com.example.backend.controller;

import com.example.backend.model.respon.ApiDataResponse;
import com.example.backend.model.respon.NotificationResponse;
import com.example.backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<NotificationResponse>>> getNotifications(
            @RequestParam Long currentUserId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<NotificationResponse> notifications = notificationService.getNotifications(currentUserId, PageRequest.of(page, size));
        return ResponseEntity.ok(ApiDataResponse.<Page<NotificationResponse>>builder()
                .status(true)
                .message("Notifications retrieved")
                .data(notifications)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<ApiDataResponse<Boolean>> markNotificationAsRead(
            @RequestParam Long currentUserId,
            @PathVariable Long notificationId) {
        Boolean result = notificationService.markNotificationAsRead(currentUserId, notificationId);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Notification marked as read")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @PutMapping("/read-all")
    public ResponseEntity<ApiDataResponse<Boolean>> markAllNotificationsAsRead(
            @RequestParam Long currentUserId) {
        Boolean result = notificationService.markAllNotificationsAsRead(currentUserId);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("All notifications marked as read")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }
}
