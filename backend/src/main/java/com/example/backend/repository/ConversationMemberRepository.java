package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.model.entity.ConversationMember;
import com.example.backend.model.entity.ConversationMemberId;
import com.example.backend.model.entity.User;

@Repository
public interface ConversationMemberRepository extends JpaRepository<ConversationMember, ConversationMemberId> {

    // ==================== MODULE 11: CHAT MEMBERS ====================

    // Lấy danh sách thành viên trong 1 cuộc hội thoại
    @Query("SELECT cm FROM ConversationMember cm JOIN FETCH cm.user WHERE cm.conversation.id = :conversationId")
    List<ConversationMember> findByConversationId(@Param("conversationId") Long conversationId);

    // Lấy danh sách user trong cuộc hội thoại (trả về danh sách Entity User trực tiếp)
    @Query("SELECT cm.user FROM ConversationMember cm WHERE cm.conversation.id = :conversationId")
    List<User> findUsersByConversationId(@Param("conversationId") Long conversationId);

    // Lấy danh sách ID của các thành viên trong cuộc hội thoại (dùng để gửi websocket notification)
    @Query("SELECT cm.user.id FROM ConversationMember cm WHERE cm.conversation.id = :conversationId")
    List<Long> findUserIdsByConversationId(@Param("conversationId") Long conversationId);

    // Kiểm tra xem user có phải là thành viên của cuộc hội thoại hay không (phân quyền xem tin nhắn)
    boolean existsByConversationIdAndUserId(Long conversationId, Long userId);

    // Đếm số lượng thành viên trong cuộc hội thoại
    Long countByConversationId(Long conversationId);

    // Rời nhóm chat hoặc xóa thành viên khỏi nhóm chat
    @Modifying
    @Query("DELETE FROM ConversationMember cm WHERE cm.conversation.id = :conversationId AND cm.user.id = :userId")
    void removeMember(@Param("conversationId") Long conversationId, @Param("userId") Long userId);
}
