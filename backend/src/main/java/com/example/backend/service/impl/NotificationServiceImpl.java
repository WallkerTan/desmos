package com.example.backend.service.impl;

import com.example.backend.model.mapper.NotificationMapper;
import com.example.backend.model.respon.NotificationResponse;
import com.example.backend.repository.NotificationRepository;
import com.example.backend.repository.UserReposetory;
import com.example.backend.service.NotificationService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserReposetory userRepository;
    private final NotificationMapper notificationMapper;

    // Lấy danh sách thông báo của người dùng (phân trang, mới nhất lên trước)
    @Override
    public Page<NotificationResponse> getNotifications(Long userId, Pageable pageable) {
        // Kiểm tra user có tồn tại không
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Không tìm thấy người dùng với id: " + userId);
        }

        return notificationRepository.findByUserId(userId, pageable)
                .map(notificationMapper::toResponse);
    }

    // Đánh dấu 1 thông báo là đã đọc
    @Override
    @Transactional
    public Boolean markNotificationAsRead(Long userId, Long notificationId) {
        // Kiểm tra user có tồn tại không
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Không tìm thấy người dùng với id: " + userId);
        }

        // Kiểm tra thông báo có tồn tại không
        if (!notificationRepository.existsById(notificationId)) {
            throw new RuntimeException("Không tìm thấy thông báo với id: " + notificationId);
        }

        notificationRepository.markAsRead(notificationId, userId);
        return true;
    }

    // Đánh dấu tất cả thông báo là đã đọc
    @Override
    @Transactional
    public Boolean markAllNotificationsAsRead(Long userId) {
        // Kiểm tra user có tồn tại không
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Không tìm thấy người dùng với id: " + userId);
        }

        notificationRepository.markAllAsRead(userId);
        return true;
    }
}
