package com.example.backend.service.impl;

import com.example.backend.model.entity.Follow;
import com.example.backend.model.entity.Notification;
import com.example.backend.model.entity.User;
import com.example.backend.model.enums.NotificationType;
import com.example.backend.repository.FollowRepository;
import com.example.backend.repository.FriendRepository;
import com.example.backend.repository.NotificationRepository;
import com.example.backend.repository.UserReposetory;
import com.example.backend.service.FollowService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserReposetory userRepository;
    private final FriendRepository friendRepository;
    private final NotificationRepository notificationRepository;
    private final com.example.backend.model.mapper.UserMapper userMapper;

    // Theo dõi người dùng
    @Override
    @Transactional
    public Boolean followUser(Long followerId, Long followingId) {
        // Kiểm tra không được tự follow chính mình
        if (followerId.equals(followingId)) {
            throw new RuntimeException("Bạn không thể theo dõi chính mình");
        }

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với id: " + followerId));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với id: " + followingId));

        // Kiểm tra xem có bị chặn không
        if (friendRepository.isAnyBlocked(followerId, followingId)) {
            throw new RuntimeException("Không thể theo dõi người dùng này");
        }

        // Kiểm tra đã follow chưa
        if (followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new RuntimeException("Bạn đã theo dõi người dùng này rồi");
        }

        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .createAt(LocalDateTime.now())
                .build();
        followRepository.save(follow);

        // Gửi thông báo cho người được theo dõi
        Notification notification = Notification.builder()
                .user(following)
                .type(NotificationType.FRIEND_REQUEST)
                .message(follower.getUsername() + " đã bắt đầu theo dõi bạn")
                .targetId(followerId)
                .action("/profile/" + followerId)
                .isRead(false)
                .build();
        notificationRepository.save(notification);

        return true;
    }

    // Bỏ theo dõi người dùng
    @Override
    @Transactional
    public Boolean unfollowUser(Long followerId, Long followingId) {
        // Kiểm tra không được tự unfollow chính mình
        if (followerId.equals(followingId)) {
            throw new RuntimeException("Bạn không thể bỏ theo dõi chính mình");
        }

        // Kiểm tra đã follow chưa
        if (!followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new RuntimeException("Bạn chưa theo dõi người dùng này");
        }

        followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
        return true;
    }

    // Lấy danh sách người theo dõi mình (Followers)
    @Override
    public org.springframework.data.domain.Page<com.example.backend.model.respon.UserResponse> getFollowers(Long userId, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        return followRepository.findFollowersByUserId(userId, pageable)
                .map(follow -> userMapper.toResponse(follow.getFollower()));
    }

    // Lấy danh sách người mình đang theo dõi (Following)
    @Override
    public org.springframework.data.domain.Page<com.example.backend.model.respon.UserResponse> getFollowing(Long userId, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        return followRepository.findFollowingByUserId(userId, pageable)
                .map(follow -> userMapper.toResponse(follow.getFollowing()));
    }
}
