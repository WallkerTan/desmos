package com.example.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.backend.model.respon.NotificationResponse;

public interface NotificationService {
    // lấy danh sách thông báo
    Page<NotificationResponse> getNotifications(Long userId, Pageable pageable);

    // đánh dấu 1 thông báo là đã đọc
    Boolean markNotificationAsRead(Long userId, Long notificationId);

    // đánh dấu tất cả thông báo là đã đọc
    Boolean markAllNotificationsAsRead(Long userId);
}
