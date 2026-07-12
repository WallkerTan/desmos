package com.example.backend.service.impl;

import com.example.backend.model.entity.*;
import com.example.backend.repository.*;
import com.example.backend.service.MessageService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final ConversationRepository conversationRepository;
    private final ConversationMemberRepository conversationMemberRepository;
    private final MessageRepository messageRepository;
    private final MessageStatusRepository messageStatusRepository;
    private final UserReposetory userRepository;

    // Tạo hoặc lấy cuộc hội thoại 1-1 giữa 2 người dùng
    @Override
    @Transactional
    public Conversation createOrGetPrivateConversation(Long userId1, Long userId2) {
        // Kiểm tra không được chat với chính mình
        if (userId1.equals(userId2)) {
            throw new RuntimeException("Không thể tạo cuộc hội thoại với chính mình");
        }

        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với id: " + userId1));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với id: " + userId2));

        // Kiểm tra xem đã tồn tại cuộc hội thoại 1-1 chưa
        return conversationRepository.findPrivateConversation(userId1, userId2)
                .orElseGet(() -> {
                    // Tạo cuộc hội thoại mới
                    Conversation conversation = Conversation.builder()
                            .isGroup(false)
                            .creator(user1)
                            .createAt(LocalDateTime.now())
                            .updateAt(LocalDateTime.now())
                            .build();
                    Conversation savedConversation = conversationRepository.save(conversation);

                    // Thêm 2 thành viên vào cuộc hội thoại
                    ConversationMember member1 = ConversationMember.builder()
                            .conversation(savedConversation)
                            .user(user1)
                            .joinedAt(LocalDateTime.now())
                            .build();
                    ConversationMember member2 = ConversationMember.builder()
                            .conversation(savedConversation)
                            .user(user2)
                            .joinedAt(LocalDateTime.now())
                            .build();
                    conversationMemberRepository.save(member1);
                    conversationMemberRepository.save(member2);

                    return savedConversation;
                });
    }

    // Tạo nhóm chat mới
    @Override
    @Transactional
    public Conversation createGroupConversation(Long creatorId, String name, List<Long> memberIds) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người tạo nhóm với id: " + creatorId));

        // Kiểm tra tên nhóm chat không được để trống
        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("Tên nhóm chat không được để trống");
        }

        // Kiểm tra số lượng thành viên tối thiểu (ít nhất 2 người ngoài creator)
        if (memberIds == null || memberIds.size() < 2) {
            throw new RuntimeException("Nhóm chat cần ít nhất 3 thành viên (bao gồm người tạo)");
        }

        // Tạo cuộc hội thoại nhóm
        Conversation conversation = Conversation.builder()
                .name(name)
                .isGroup(true)
                .creator(creator)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
        Conversation savedConversation = conversationRepository.save(conversation);

        // Thêm creator vào nhóm
        ConversationMember creatorMember = ConversationMember.builder()
                .conversation(savedConversation)
                .user(creator)
                .joinedAt(LocalDateTime.now())
                .build();
        conversationMemberRepository.save(creatorMember);

        // Thêm các thành viên khác vào nhóm
        for (Long memberId : memberIds) {
            if (memberId.equals(creatorId)) {
                continue; // Bỏ qua nếu trùng với creator
            }
            User member = userRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thành viên với id: " + memberId));

            ConversationMember conversationMember = ConversationMember.builder()
                    .conversation(savedConversation)
                    .user(member)
                    .joinedAt(LocalDateTime.now())
                    .build();
            conversationMemberRepository.save(conversationMember);
        }

        return savedConversation;
    }

    // Gửi tin nhắn trong cuộc hội thoại
    @Override
    @Transactional
    public Message sendMessage(Long senderId, Long conversationId, String content, String mediaUrl, String emoji) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người gửi với id: " + senderId));

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc hội thoại với id: " + conversationId));

        // Kiểm tra người gửi có phải là thành viên của cuộc hội thoại không
        if (!conversationMemberRepository.existsByConversationIdAndUserId(conversationId, senderId)) {
            throw new RuntimeException("Bạn không phải là thành viên của cuộc hội thoại này");
        }

        // Kiểm tra nội dung tin nhắn không được hoàn toàn trống
        if ((content == null || content.trim().isEmpty())
                && (mediaUrl == null || mediaUrl.trim().isEmpty())
                && (emoji == null || emoji.trim().isEmpty())) {
            throw new RuntimeException("Tin nhắn không được để trống");
        }

        // Tạo tin nhắn
        Message message = Message.builder()
                .conversation(conversation)
                .sender(sender)
                .content(content)
                .mediaUrl(mediaUrl)
                .emoji(emoji)
                .isRecalled(false)
                .createAt(LocalDateTime.now())
                .build();
        Message savedMessage = messageRepository.save(message);

        // Cập nhật thời gian cuộc hội thoại (để sắp xếp danh sách chat)
        conversation.setUpdateAt(LocalDateTime.now());
        conversationRepository.save(conversation);

        // Tạo trạng thái đã xem cho tất cả thành viên (trừ người gửi)
        List<Long> memberIds = conversationMemberRepository.findUserIdsByConversationId(conversationId);
        for (Long memberId : memberIds) {
            if (!memberId.equals(senderId)) {
                MessageStatus status = MessageStatus.builder()
                        .message(savedMessage)
                        .user(User.builder().id(memberId).build())
                        .isSeen(false)
                        .build();
                messageStatusRepository.save(status);
            }
        }

        return savedMessage;
    }

    // Thu hồi tin nhắn (chỉ người gửi có quyền)
    @Override
    @Transactional
    public Boolean recallMessage(Long senderId, Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tin nhắn với id: " + messageId));

        // Kiểm tra quyền thu hồi (chỉ người gửi)
        if (!message.getSender().getId().equals(senderId)) {
            throw new RuntimeException("Bạn không có quyền thu hồi tin nhắn này");
        }

        // Kiểm tra đã thu hồi chưa
        if (Boolean.TRUE.equals(message.getIsRecalled())) {
            throw new RuntimeException("Tin nhắn này đã được thu hồi");
        }

        messageRepository.recallMessage(messageId, senderId);
        return true;
    }

    // Lấy danh sách tin nhắn của cuộc hội thoại (phân trang)
    @Override
    public Page<Message> getMessages(Long userId, Long conversationId, Pageable pageable) {
        // Kiểm tra quyền xem (user phải là thành viên)
        if (!conversationMemberRepository.existsByConversationIdAndUserId(conversationId, userId)) {
            throw new RuntimeException("Bạn không phải là thành viên của cuộc hội thoại này");
        }

        return messageRepository.findByConversationIdOrderByCreateAtDesc(conversationId, pageable);
    }

    // Đánh dấu tất cả tin nhắn trong cuộc hội thoại là đã xem
    @Override
    @Transactional
    public Boolean markConversationAsSeen(Long userId, Long conversationId) {
        // Kiểm tra quyền (user phải là thành viên)
        if (!conversationMemberRepository.existsByConversationIdAndUserId(conversationId, userId)) {
            throw new RuntimeException("Bạn không phải là thành viên của cuộc hội thoại này");
        }

        messageStatusRepository.markAllMessagesInConversationAsSeen(
                conversationId, userId, LocalDateTime.now());
        return true;
    }
}
