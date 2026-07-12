package com.example.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.model.entity.MessageStatus;
import com.example.backend.model.entity.MessageStatusId;

@Repository
public interface MessageStatusRepository extends JpaRepository<MessageStatus, MessageStatusId> {

    // ==================== MODULE 11: CHAT MESSAGE SEEN STATUS ====================

    // Lấy trạng thái xem tin nhắn của từng thành viên đối với 1 tin nhắn cụ thể
    List<MessageStatus> findByMessageId(Long messageId);

    // Cập nhật trạng thái "Đã xem" (Seen) cho một tin nhắn cụ thể của một user cụ thể
    @Modifying
    @Query("UPDATE MessageStatus ms SET ms.isSeen = true, ms.seenAt = :seenAt " +
           "WHERE ms.message.id = :messageId AND ms.user.id = :userId")
    void markAsSeen(@Param("messageId") Long messageId, 
                    @Param("userId") Long userId, 
                    @Param("seenAt") LocalDateTime seenAt);

    // Đánh dấu "Đã xem" cho tất cả các tin nhắn chưa xem trong cuộc hội thoại của một user cụ thể
    @Modifying
    @Query("UPDATE MessageStatus ms SET ms.isSeen = true, ms.seenAt = :seenAt " +
           "WHERE ms.user.id = :userId AND ms.isSeen = false " +
           "AND ms.message.id IN (SELECT m.id FROM Message m WHERE m.conversation.id = :conversationId)")
    void markAllMessagesInConversationAsSeen(@Param("conversationId") Long conversationId, 
                                            @Param("userId") Long userId, 
                                            @Param("seenAt") LocalDateTime seenAt);

    // Đếm số lượng tin nhắn chưa đọc của 1 user trong 1 cuộc hội thoại cụ thể (để hiển thị số tin nhắn chưa đọc)
    @Query("SELECT COUNT(ms) FROM MessageStatus ms WHERE ms.user.id = :userId AND ms.isSeen = false " +
           "AND ms.message.conversation.id = :conversationId")
    Long countUnreadMessagesInConversation(@Param("conversationId") Long conversationId, 
                                           @Param("userId") Long userId);

    // Đếm tổng số cuộc hội thoại có tin nhắn chưa đọc của 1 user (hiển thị badge trên icon Chat chính)
    @Query("SELECT COUNT(DISTINCT ms.message.conversation.id) FROM MessageStatus ms " +
           "WHERE ms.user.id = :userId AND ms.isSeen = false")
    Long countTotalConversationsWithUnreadMessages(@Param("userId") Long userId);
}
