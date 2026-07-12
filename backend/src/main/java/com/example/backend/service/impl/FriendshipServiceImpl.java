package com.example.backend.service.impl;

import com.example.backend.model.entity.*;
import com.example.backend.model.enums.FriendStatus;
import com.example.backend.model.enums.NotificationType;
import com.example.backend.model.mapper.FriendMapper;
import com.example.backend.model.mapper.FriendshipMapper;
import com.example.backend.model.respon.FriendResponse;
import com.example.backend.model.respon.FriendshipResponse;
import com.example.backend.repository.*;
import com.example.backend.service.FriendshipService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final FriendRepository friendRepository;
    private final FollowRepository followRepository;
    private final UserReposetory userRepository;
    private final NotificationRepository notificationRepository;
    private final FriendshipMapper friendshipMapper;
    private final FriendMapper friendMapper;

    // Gửi lời mời kết bạn
    @Override
    @Transactional
    public FriendshipResponse sendFriendRequest(Long senderId, Long receiverId) {
        // Kiểm tra không được gửi lời mời cho chính mình
        if (senderId.equals(receiverId)) {
            throw new RuntimeException("Bạn không thể gửi lời mời kết bạn cho chính mình");
        }

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người gửi với id: " + senderId));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người nhận với id: " + receiverId));

        // Kiểm tra xem có bị chặn không
        if (friendRepository.isAnyBlocked(senderId, receiverId)) {
            throw new RuntimeException("Không thể gửi lời mời kết bạn cho người dùng này");
        }

        // Kiểm tra đã là bạn bè chưa
        if (friendRepository.isFriend(senderId, receiverId)) {
            throw new RuntimeException("Hai người đã là bạn bè");
        }

        // Kiểm tra đã gửi lời mời chưa (cả 2 chiều)
        if (friendshipRepository.existsPendingRequest(senderId, receiverId)) {
            throw new RuntimeException("Đã tồn tại lời mời kết bạn giữa hai người");
        }

        Friendship friendship = Friendship.builder()
                .sender(sender)
                .receiver(receiver)
                .createAt(LocalDateTime.now())
                .build();

        Friendship saved = friendshipRepository.save(friendship);

        // Gửi thông báo cho người nhận
        Notification notification = Notification.builder()
                .user(receiver)
                .type(NotificationType.FRIEND_REQUEST)
                .message(sender.getUsername() + " đã gửi cho bạn lời mời kết bạn")
                .targetId(senderId)
                .action("/profile/" + senderId)
                .isRead(false)
                .build();
        notificationRepository.save(notification);

        return friendshipMapper.toResponse(saved);
    }

    // Chấp nhận lời mời kết bạn
    @Override
    @Transactional
    public FriendResponse acceptFriendRequest(Long receiverId, Long senderId) {
        // Tìm lời mời kết bạn
        Friendship friendship = friendshipRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lời mời kết bạn"));

        User user1 = friendship.getSender();
        User user2 = friendship.getReceiver();

        // Tạo mối quan hệ bạn bè
        Friend friend = Friend.builder()
                .user1(user1)
                .user2(user2)
                .status(FriendStatus.friend)
                .createAt(LocalDateTime.now())
                .build();
        Friend savedFriend = friendRepository.save(friend);

        // Xóa tất cả lời mời kết bạn giữa 2 user (cả 2 chiều nếu có)
        friendshipRepository.deleteAllBetweenUsers(senderId, receiverId);

        // Tự động follow lẫn nhau khi kết bạn
        if (!followRepository.existsByFollowerIdAndFollowingId(user1.getId(), user2.getId())) {
            Follow follow1 = Follow.builder()
                    .follower(user1)
                    .following(user2)
                    .createAt(LocalDateTime.now())
                    .build();
            followRepository.save(follow1);
        }
        if (!followRepository.existsByFollowerIdAndFollowingId(user2.getId(), user1.getId())) {
            Follow follow2 = Follow.builder()
                    .follower(user2)
                    .following(user1)
                    .createAt(LocalDateTime.now())
                    .build();
            followRepository.save(follow2);
        }

        // Gửi thông báo cho người gửi lời mời
        Notification notification = Notification.builder()
                .user(user1)
                .type(NotificationType.FRIEND_ACCEPTED)
                .message(user2.getUsername() + " đã chấp nhận lời mời kết bạn của bạn")
                .targetId(receiverId)
                .action("/profile/" + receiverId)
                .isRead(false)
                .build();
        notificationRepository.save(notification);

        return friendMapper.toResponse(savedFriend);
    }

    // Từ chối lời mời kết bạn
    @Override
    @Transactional
    public Boolean rejectFriendRequest(Long receiverId, Long senderId) {
        // Kiểm tra lời mời có tồn tại không
        friendshipRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lời mời kết bạn"));

        friendshipRepository.deleteBySenderIdAndReceiverId(senderId, receiverId);
        return true;
    }

    // Hủy lời mời kết bạn đã gửi
    @Override
    @Transactional
    public Boolean cancelFriendRequest(Long senderId, Long receiverId) {
        // Kiểm tra lời mời có tồn tại không
        friendshipRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lời mời kết bạn đã gửi"));

        friendshipRepository.deleteBySenderIdAndReceiverId(senderId, receiverId);
        return true;
    }

    // Hủy kết bạn (unfriend)
    @Override
    @Transactional
    public Boolean unfriend(Long userId, Long friendId) {
        // Tìm mối quan hệ bạn bè
        Friend friend = friendRepository.findRelationship(userId, friendId)
                .orElseThrow(() -> new RuntimeException("Hai người không phải là bạn bè"));

        // Kiểm tra trạng thái có phải đang là bạn bè không
        if (friend.getStatus() != FriendStatus.friend) {
            throw new RuntimeException("Hai người không phải là bạn bè");
        }

        // Xóa mềm mối quan hệ bạn bè
        friendRepository.softDelete(friend.getId());
        return true;
    }

    // Chặn người dùng
    @Override
    @Transactional
    public Boolean blockUser(Long blockerId, Long blockedId) {
        // Kiểm tra không được tự chặn chính mình
        if (blockerId.equals(blockedId)) {
            throw new RuntimeException("Bạn không thể chặn chính mình");
        }

        User blocker = userRepository.findById(blockerId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với id: " + blockerId));
        User blocked = userRepository.findById(blockedId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với id: " + blockedId));

        // Kiểm tra đã chặn chưa
        if (friendRepository.isBlocked(blockerId, blockedId)) {
            throw new RuntimeException("Bạn đã chặn người dùng này rồi");
        }

        // Nếu đang là bạn bè thì hủy kết bạn trước
        friendRepository.findRelationship(blockerId, blockedId)
                .ifPresent(existingFriend -> friendRepository.softDelete(existingFriend.getId()));

        // Xóa lời mời kết bạn nếu có
        friendshipRepository.deleteAllBetweenUsers(blockerId, blockedId);

        // Xóa follow lẫn nhau nếu có
        if (followRepository.existsByFollowerIdAndFollowingId(blockerId, blockedId)) {
            followRepository.deleteByFollowerIdAndFollowingId(blockerId, blockedId);
        }
        if (followRepository.existsByFollowerIdAndFollowingId(blockedId, blockerId)) {
            followRepository.deleteByFollowerIdAndFollowingId(blockedId, blockerId);
        }

        // Tạo bản ghi block
        Friend blockRecord = Friend.builder()
                .user1(blocker)
                .user2(blocked)
                .status(FriendStatus.block)
                .createAt(LocalDateTime.now())
                .build();
        friendRepository.save(blockRecord);

        return true;
    }

    // Bỏ chặn người dùng
    @Override
    @Transactional
    public Boolean unblockUser(Long blockerId, Long blockedId) {
        if (!friendRepository.isBlocked(blockerId, blockedId)) {
            throw new RuntimeException("Người dùng này chưa bị chặn");
        }
        
        Friend relationship = friendRepository.findRelationship(blockerId, blockedId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi chặn"));
        
        // Cập nhật trạng thái hoặc xóa mềm bản ghi chặn
        friendRepository.softDelete(relationship.getId());
        return true;
    }

    // Lấy danh sách bạn bè
    @Override
    public java.util.List<FriendResponse> getFriends(Long userId) {
        return friendRepository.findAllFriends(userId)
                .stream()
                .map(friendMapper::toResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    // Lấy danh sách lời mời kết bạn (Pending Requests)
    @Override
    public org.springframework.data.domain.Page<FriendshipResponse> getPendingRequests(Long userId, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        return friendshipRepository.findByReceiverId(userId, pageable)
                .map(friendshipMapper::toResponse);
    }

    // Lấy danh sách người dùng đã bị chặn
    @Override
    public java.util.List<FriendResponse> getBlockedUsers(Long userId) {
        return friendRepository.findBlockedByUser(userId)
                .stream()
                .map(friendMapper::toResponse)
                .collect(java.util.stream.Collectors.toList());
    }
}
