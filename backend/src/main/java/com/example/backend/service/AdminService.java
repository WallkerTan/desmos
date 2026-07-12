package com.example.backend.service;

public interface AdminService {
    //quản lí người dùng
    org.springframework.data.domain.Page<com.example.backend.model.respon.UserResponse> getAllUsers(org.springframework.data.domain.Pageable pageable);
    org.springframework.data.domain.Page<com.example.backend.model.respon.UserResponse> filterUsersByActive(Boolean isActive, org.springframework.data.domain.Pageable pageable);
    org.springframework.data.domain.Page<com.example.backend.model.respon.UserResponse> filterUsersByRole(com.example.backend.model.enums.Role role, org.springframework.data.domain.Pageable pageable);
    Boolean lockOrUnlockUser(Long userId, Boolean isActive);

    //quản lí bài viết
    org.springframework.data.domain.Page<com.example.backend.model.respon.PostResponse> getAllPosts(org.springframework.data.domain.Pageable pageable);
    org.springframework.data.domain.Page<com.example.backend.model.respon.PostResponse> filterPostsByStatus(com.example.backend.model.enums.PostStatus status, org.springframework.data.domain.Pageable pageable);
    Boolean deletePost(Long postId);

    //báo cáo thống kê
    Long countNewUsers(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate);
    Long countActiveUsers();
    Long countNewPosts(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate);
    Long countNewComments(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate);
    Long countNewReacts(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate);
}
