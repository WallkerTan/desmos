package com.example.backend.service.impl;

import com.example.backend.model.entity.User;
import com.example.backend.model.entity.Post;
import com.example.backend.model.enums.Role;
import com.example.backend.model.enums.PostStatus;
import com.example.backend.model.respon.UserResponse;
import com.example.backend.model.respon.PostResponse;
import com.example.backend.model.mapper.UserMapper;
import com.example.backend.model.mapper.PostMapper;
import com.example.backend.repository.UserReposetory;
import com.example.backend.repository.PostRepository;
import com.example.backend.repository.CommentRepository;
import com.example.backend.repository.ReactRepository;
import com.example.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserReposetory userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReactRepository reactRepository;
    private final UserMapper userMapper;
    private final PostMapper postMapper;

    // Lấy danh sách tất cả người dùng (phân trang)
    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

    // Lọc người dùng theo trạng thái hoạt động (active/inactive)
    @Override
    public Page<UserResponse> filterUsersByActive(Boolean isActive, Pageable pageable) {
        return userRepository.findByIsActive(isActive, pageable)
                .map(userMapper::toResponse);
    }

    // Lọc người dùng theo vai trò (role)
    @Override
    public Page<UserResponse> filterUsersByRole(Role role, Pageable pageable) {
        return userRepository.findByRole(role, pageable)
                .map(userMapper::toResponse);
    }

    // Khóa hoặc mở khóa tài khoản người dùng
    @Override
    @Transactional
    public Boolean lockOrUnlockUser(Long userId, Boolean isActive) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        userRepository.updateActiveStatus(userId, isActive);
        return true;
    }

    // Lấy danh sách tất cả bài viết cho admin (phân trang)
    @Override
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        return postRepository.findAllForAdmin(pageable)
                .map(postMapper::toResponse);
    }

    // Lọc bài viết theo trạng thái (status)
    @Override
    public Page<PostResponse> filterPostsByStatus(PostStatus status, Pageable pageable) {
        return postRepository.findByStatus(status, pageable)
                .map(postMapper::toResponse);
    }

    // Admin xóa mềm bài viết
    @Override
    @Transactional
    public Boolean deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new RuntimeException("Post not found with id: " + postId);
        }
        postRepository.adminSoftDeletePost(postId);
        return true;
    }

    // Thống kê số người dùng mới trong khoảng thời gian
    @Override
    public Long countNewUsers(LocalDateTime startDate, LocalDateTime endDate) {
        return userRepository.countNewUsersInPeriod(startDate, endDate);
    }

    // Thống kê số người dùng đang hoạt động
    @Override
    public Long countActiveUsers() {
        return userRepository.countByIsActiveTrue();
    }

    // Thống kê số bài viết mới trong khoảng thời gian
    @Override
    public Long countNewPosts(LocalDateTime startDate, LocalDateTime endDate) {
        return postRepository.countPostsInPeriod(startDate, endDate);
    }

    // Thống kê số bình luận mới trong khoảng thời gian
    @Override
    public Long countNewComments(LocalDateTime startDate, LocalDateTime endDate) {
        return commentRepository.countCommentsInPeriod(startDate, endDate);
    }

    // Thống kê số lượt react mới trong khoảng thời gian
    @Override
    public Long countNewReacts(LocalDateTime startDate, LocalDateTime endDate) {
        return reactRepository.countReactsInPeriod(startDate, endDate);
    }
}
