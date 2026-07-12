package com.example.backend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.model.entity.Friendship;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    // ==================== MODULE 3: FRIENDSHIP (Lời mời kết bạn) ====================

    // Tìm lời mời kết bạn giữa sender và receiver
    Optional<Friendship> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    // Kiểm tra đã gửi lời mời chưa (bất kể chiều nào)
    @Query("SELECT CASE WHEN COUNT(fs) > 0 THEN true ELSE false END FROM Friendship fs " +
           "WHERE (fs.sender.id = :userId1 AND fs.receiver.id = :userId2) " +
           "OR (fs.sender.id = :userId2 AND fs.receiver.id = :userId1)")
    boolean existsPendingRequest(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    // Lấy danh sách lời mời kết bạn đã nhận (receiver = userId)
    Page<Friendship> findByReceiverId(Long receiverId, Pageable pageable);

    // Lấy danh sách lời mời kết bạn đã gửi (sender = userId)
    Page<Friendship> findBySenderId(Long senderId, Pageable pageable);

    // Đếm số lời mời kết bạn chưa xử lý (hiển thị badge notification)
    Long countByReceiverId(Long receiverId);

    // Xóa lời mời kết bạn (khi chấp nhận, từ chối, hoặc hủy)
    @Modifying
    @Query("DELETE FROM Friendship fs WHERE fs.sender.id = :senderId AND fs.receiver.id = :receiverId")
    void deleteBySenderIdAndReceiverId(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    // Xóa tất cả lời mời liên quan đến 1 cặp user (khi chấp nhận kết bạn - xóa cả 2 chiều nếu có)
    @Modifying
    @Query("DELETE FROM Friendship fs WHERE " +
           "(fs.sender.id = :userId1 AND fs.receiver.id = :userId2) " +
           "OR (fs.sender.id = :userId2 AND fs.receiver.id = :userId1)")
    void deleteAllBetweenUsers(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    // Lấy danh sách tất cả lời mời kết bạn (dùng cho admin)
    @Query("SELECT fs FROM Friendship fs ORDER BY fs.createAt DESC")
    Page<Friendship> findAllOrderByCreateAtDesc(Pageable pageable);
}
