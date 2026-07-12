package com.example.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.model.entity.Conversation;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    // ==================== MODULE 11: CHAT ====================

    // Lấy tất cả phòng chat mà user tham gia, sắp xếp theo thời gian cập nhật mới nhất (tin nhắn mới nhất)
    @Query("SELECT c FROM Conversation c JOIN ConversationMember cm ON c.id = cm.conversation.id " +
           "WHERE cm.user.id = :userId ORDER BY c.updateAt DESC")
    Page<Conversation> findConversationsByUserId(@Param("userId") Long userId, Pageable pageable);

    // Kiểm tra xem đã tồn tại cuộc hội thoại 1-1 giữa 2 user chưa
    @Query("SELECT c FROM Conversation c " +
           "WHERE c.isGroup = false " +
           "AND c.id IN (SELECT cm1.conversation.id FROM ConversationMember cm1 WHERE cm1.user.id = :userId1) " +
           "AND c.id IN (SELECT cm2.conversation.id FROM ConversationMember cm2 WHERE cm2.user.id = :userId2)")
    Optional<Conversation> findPrivateConversation(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    // Lấy tất cả phòng chat nhóm do user sáng lập
    List<Conversation> findByCreatorIdAndIsGroupTrue(Long creatorId);
}
