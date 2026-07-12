package com.example.backend.service;

import com.example.backend.model.respon.FriendResponse;
import com.example.backend.model.respon.FriendshipResponse;

public interface FriendshipService {
    // gửi lời mời kết bạn
    FriendshipResponse sendFriendRequest(Long senderId, Long receiverId);

    // chấp nhận lời mời kết bạn
    FriendResponse acceptFriendRequest(Long receiverId, Long senderId);

    // từ chối lời mời kết bạn
    Boolean rejectFriendRequest(Long receiverId, Long senderId);

    // hủy lời mời kết bạn đã gửi
    Boolean cancelFriendRequest(Long senderId, Long receiverId);

    // hủy kết bạn
    Boolean unfriend(Long userId, Long friendId);

    // chặn người dùng
    Boolean blockUser(Long blockerId, Long blockedId);

    // bỏ chặn người dùng
    Boolean unblockUser(Long blockerId, Long blockedId);

    // lấy danh sách bạn bè
    java.util.List<FriendResponse> getFriends(Long userId);

    // lấy danh sách lời mời kết bạn (pending requests)
    org.springframework.data.domain.Page<FriendshipResponse> getPendingRequests(Long userId, int page, int size);

    // lấy danh sách người dùng đã bị chặn
    java.util.List<FriendResponse> getBlockedUsers(Long userId);
}
