package com.example.backend.service.impl;

import com.example.backend.model.entity.*;
import com.example.backend.model.enums.NotificationType;
import com.example.backend.model.request.CommentRequest;
import com.example.backend.model.respon.CommentResponse;
import com.example.backend.model.mapper.CommentMapper;
import com.example.backend.repository.CommentRepository;
import com.example.backend.repository.NotificationRepository;
import com.example.backend.repository.PostRepository;
import com.example.backend.repository.UserReposetory;
import com.example.backend.service.CommentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserReposetory userRepository;
    private final PostRepository postRepository;
    private final NotificationRepository notificationRepository;
    private final CommentMapper commentMapper;

    // Thêm bình luận mới (bao gồm reply bình luận và gửi thông báo)
    @Override
    @Transactional
    public CommentResponse createComment(Long userId, CommentRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = commentMapper.toEntity(request);
        comment.setUser(user);
        comment.setPost(post);
        comment.setCreateAt(LocalDateTime.now());

        if (request.getParentCommentId() != null) {
            Comment parent = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
            comment.setComment(parent);
        }

        Comment saved = commentRepository.save(comment);

        // Gửi thông báo
        if (request.getParentCommentId() != null) {
            Comment parent = comment.getComment();
            if (!parent.getUser().getId().equals(userId)) {
                Notification notification = Notification.builder()
                        .user(parent.getUser())
                        .type(NotificationType.COMMENT_REPLY)
                        .message(user.getUsername() + " đã trả lời bình luận của bạn")
                        .targetId(post.getId())
                        .action("/posts/" + post.getId())
                        .isRead(false)
                        .build();
                notificationRepository.save(notification);
            }
        } else {
            if (!post.getUser().getId().equals(userId)) {
                Notification notification = Notification.builder()
                        .user(post.getUser())
                        .type(NotificationType.POST_COMMENT)
                        .message(user.getUsername() + " đã bình luận về bài viết của bạn")
                        .targetId(post.getId())
                        .action("/posts/" + post.getId())
                        .isRead(false)
                        .build();
                notificationRepository.save(notification);
            }
        }

        return commentMapper.toResponse(saved);
    }

    // Chỉnh sửa nội dung bình luận
    @Override
    @Transactional
    public CommentResponse updateComment(Long userId, Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("You do not have permission to edit this comment");
        }

        comment.setContent(content);
        comment.setUpdateAt(LocalDateTime.now());
        Comment saved = commentRepository.save(comment);
        return commentMapper.toResponse(saved);
    }

    // Xóa mềm bình luận (cho phép chủ bình luận hoặc chủ bài viết xóa)
    @Override
    @Transactional
    public Boolean deleteComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // Cho phép chủ bình luận hoặc chủ bài viết xóa
        if (!comment.getUser().getId().equals(userId) && !comment.getPost().getUser().getId().equals(userId)) {
            throw new RuntimeException("You do not have permission to delete this comment");
        }

        comment.setDeleteAt(LocalDateTime.now());
        commentRepository.save(comment);
        // Xóa mềm tất cả reply
        commentRepository.softDeleteRepliesByParentId(commentId);
        return true;
    }

    // Lấy danh sách bình luận gốc của bài viết
    @Override
    public org.springframework.data.domain.Page<CommentResponse> getCommentsByPostId(Long postId, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        return commentRepository.findRootCommentsByPostId(postId, pageable)
                .map(commentMapper::toResponse);
    }

    // Lấy danh sách bình luận trả lời
    @Override
    public org.springframework.data.domain.Page<CommentResponse> getRepliesByCommentId(Long commentId, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        return commentRepository.findRepliesByCommentId(commentId, pageable)
                .map(commentMapper::toResponse);
    }
}
