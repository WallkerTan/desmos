package com.example.backend.service.impl;

import com.example.backend.model.entity.*;
import com.example.backend.model.enums.NotificationType;
import com.example.backend.model.request.ReactRequest;
import com.example.backend.model.respon.ReactResponse;
import com.example.backend.model.mapper.ReactMapper;
import com.example.backend.repository.CommentRepository;
import com.example.backend.repository.NotificationRepository;
import com.example.backend.repository.PostRepository;
import com.example.backend.repository.ReactRepository;
import com.example.backend.repository.UserReposetory;
import com.example.backend.service.ReactService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactServiceImpl implements ReactService {

    private final ReactRepository reactRepository;
    private final UserReposetory userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final NotificationRepository notificationRepository;
    private final ReactMapper reactMapper;

    // React hoặc hủy react trên bài viết/bình luận (toggle reaction)
    @Override
    @Transactional
    public ReactResponse reactPostOrComment(Long userId, ReactRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getPostId() != null) {
            return handlePostReaction(user, request);
        } else if (request.getCommentId() != null) {
            return handleCommentReaction(user, request);
        }
        throw new IllegalArgumentException("Reaction must target either a post or a comment");
    }

    private ReactResponse handlePostReaction(User user, ReactRequest request) {
        Long postId = request.getPostId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Optional<React> existing = reactRepository.findByUserIdAndPostId(user.getId(), postId);
        if (existing.isPresent()) {
            React react = existing.get();
            if (react.getReaction().equals(request.getReaction())) {
                // Undo react
                reactRepository.deleteByUserIdAndPostId(user.getId(), postId);
                return null;
            } else {
                // Change reaction type
                reactRepository.updateReactionForPost(user.getId(), postId, request.getReaction());
                react.setReaction(request.getReaction());
                return reactMapper.toResponse(react);
            }
        } else {
            React react = React.builder()
                    .user(user)
                    .post(post)
                    .reaction(request.getReaction())
                    .createAt(LocalDateTime.now())
                    .build();
            React saved = reactRepository.save(react);

            // Gửi thông báo cho chủ bài viết
            if (!post.getUser().getId().equals(user.getId())) {
                Notification notification = Notification.builder()
                        .user(post.getUser())
                        .type(NotificationType.REACTION)
                        .message(user.getUsername() + " đã bày tỏ cảm xúc về bài viết của bạn")
                        .targetId(postId)
                        .action("/posts/" + postId)
                        .isRead(false)
                        .build();
                notificationRepository.save(notification);
            }
            return reactMapper.toResponse(saved);
        }
    }

    private ReactResponse handleCommentReaction(User user, ReactRequest request) {
        Long commentId = request.getCommentId();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        Optional<React> existing = reactRepository.findByUserIdAndCommentId(user.getId(), commentId);
        if (existing.isPresent()) {
            React react = existing.get();
            if (react.getReaction().equals(request.getReaction())) {
                reactRepository.deleteByUserIdAndCommentId(user.getId(), commentId);
                return null;
            } else {
                reactRepository.updateReactionForComment(user.getId(), commentId, request.getReaction());
                react.setReaction(request.getReaction());
                return reactMapper.toResponse(react);
            }
        } else {
            React react = React.builder()
                    .user(user)
                    .comment(comment)
                    .reaction(request.getReaction())
                    .createAt(LocalDateTime.now())
                    .build();
            React saved = reactRepository.save(react);

            // Gửi thông báo cho chủ bình luận
            if (!comment.getUser().getId().equals(user.getId())) {
                Notification notification = Notification.builder()
                        .user(comment.getUser())
                        .type(NotificationType.REACTION)
                        .message(user.getUsername() + " đã bày tỏ cảm xúc về bình luận của bạn")
                        .targetId(comment.getPost().getId())
                        .action("/posts/" + comment.getPost().getId())
                        .isRead(false)
                        .build();
                notificationRepository.save(notification);
            }
            return reactMapper.toResponse(saved);
        }
    }

    // Lấy danh sách react của một bài viết
    @Override
    public java.util.List<ReactResponse> getReactionsByPostId(Long postId) {
        return reactRepository.findByPostId(postId)
                .stream()
                .map(reactMapper::toResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    // Lấy danh sách react của một bình luận
    @Override
    public java.util.List<ReactResponse> getReactionsByCommentId(Long commentId) {
        return reactRepository.findByCommentId(commentId)
                .stream()
                .map(reactMapper::toResponse)
                .collect(java.util.stream.Collectors.toList());
    }
}
