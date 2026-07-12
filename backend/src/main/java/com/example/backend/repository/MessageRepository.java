package com.example.backend.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.model.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // ==================== MODULE 11: CHAT MESSAGES ====================

    // Lấy danh sách tin nhắn của một cuộc hội thoại (phân trang, mới nhất lên đầu)
    Page<Message> findByConversationIdOrderByCreateAtDesc(Long conversationId, Pageable pageable);

    // Lấy tin nhắn mới nhất của cuộc hội thoại để hiển thị preview ở danh sách chat
    Optional<Message> findFirstByConversationIdOrderByCreateAtDesc(Long conversationId);

    // Thu hồi tin nhắn (chỉ người gửi có quyền thu hồi và trong vòng 24 giờ)
    @Modifying
    @Query("UPDATE Message m SET m.isRecalled = true WHERE m.id = :messageId AND m.sender.id = :senderId")
    void recallMessage(@Param("messageId") Long messageId, @Param("senderId") Long senderId);

    // Đếm số lượng tin nhắn trong cuộc hội thoại
    Long countByConversationId(Long conversationId);
}
