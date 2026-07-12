package com.example.backend.service;

public interface FollowService {
    // theo dõi người dùng
    Boolean followUser(Long followerId, Long followingId);

    // bỏ theo dõi người dùng
    Boolean unfollowUser(Long followerId, Long followingId);

    // Lấy danh sách người theo dõi mình (Followers)
    org.springframework.data.domain.Page<com.example.backend.model.respon.UserResponse> getFollowers(Long userId, int page, int size);

    // Lấy danh sách người mình đang theo dõi (Following)
    org.springframework.data.domain.Page<com.example.backend.model.respon.UserResponse> getFollowing(Long userId, int page, int size);
}
