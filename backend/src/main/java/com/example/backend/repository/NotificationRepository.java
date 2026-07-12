package com.example.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.model.entity.Notification;
import com.example.backend.model.enums.NotificationType;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // ==================== MODULE 9: NOTIFICATION ====================

    // Lấy danh sách thông báo của 1 user (phân trang, mới nhất trước)
    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId ORDER BY n.createdAt DESC")
    Page<Notification> findByUserId(@Param("userId") Long userId, Pageable pageable);

    // Lấy danh sách thông báo chưa đọc
    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND n.isRead = false " +
           "ORDER BY n.createdAt DESC")
    Page<Notification> findUnreadByUserId(@Param("userId") Long userId, Pageable pageable);

    // Đếm số thông báo chưa đọc (hiển thị badge)
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user.id = :userId AND n.isRead = false")
    Long countUnreadByUserId(@Param("userId") Long userId);

    // Đánh dấu 1 thông báo là đã đọc
    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.id = :notificationId AND n.user.id = :userId")
    void markAsRead(@Param("notificationId") Long notificationId, @Param("userId") Long userId);

    // Đánh dấu tất cả thông báo là đã đọc
    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.user.id = :userId AND n.isRead = false")
    void markAllAsRead(@Param("userId") Long userId);

    // Lọc thông báo theo loại (ví dụ: chỉ xem FRIEND_REQUEST, POST_LIKE, ...)
    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND n.type = :type " +
           "ORDER BY n.createdAt DESC")
    Page<Notification> findByUserIdAndType(@Param("userId") Long userId,
                                           @Param("type") NotificationType type,
                                           Pageable pageable);

    // Xóa thông báo cũ (cleanup - giữ tối đa N ngày)
    @Modifying
    @Query("DELETE FROM Notification n WHERE n.user.id = :userId " +
           "AND n.createdAt < :beforeDate AND n.isRead = true")
    void deleteOldReadNotifications(@Param("userId") Long userId,
                                     @Param("beforeDate") java.time.LocalDateTime beforeDate);

    // Tìm thông báo trùng lặp (tránh gửi thông báo lặp cho cùng 1 sự kiện)
    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND n.type = :type " +
           "AND n.targetId = :targetId ORDER BY n.createdAt DESC")
    List<Notification> findDuplicateNotifications(@Param("userId") Long userId,
                                                   @Param("type") NotificationType type,
                                                   @Param("targetId") Long targetId);

    // Kiểm tra thông báo đã tồn tại chưa (tránh spam)
    @Query("SELECT CASE WHEN COUNT(n) > 0 THEN true ELSE false END FROM Notification n " +
           "WHERE n.user.id = :userId AND n.type = :type AND n.targetId = :targetId")
    boolean existsByUserIdAndTypeAndTargetId(@Param("userId") Long userId,
                                              @Param("type") NotificationType type,
                                              @Param("targetId") Long targetId);
}
