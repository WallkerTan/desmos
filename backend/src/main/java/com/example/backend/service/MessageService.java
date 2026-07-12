package com.example.backend.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.backend.model.entity.Conversation;
import com.example.backend.model.entity.Message;

public interface MessageService {
    // tạo hoặc lấy cuộc hội thoại 1-1
    Conversation createOrGetPrivateConversation(Long userId1, Long userId2);

    // tạo nhóm chat
    Conversation createGroupConversation(Long creatorId, String name, List<Long> memberIds);

    // gửi tin nhắn
    Message sendMessage(Long senderId, Long conversationId, String content, String mediaUrl, String emoji);

    // thu hồi tin nhắn
    Boolean recallMessage(Long senderId, Long messageId);

    // lấy danh sách tin nhắn
    Page<Message> getMessages(Long userId, Long conversationId, Pageable pageable);

    // đánh dấu đã xem
    Boolean markConversationAsSeen(Long userId, Long conversationId);
}
