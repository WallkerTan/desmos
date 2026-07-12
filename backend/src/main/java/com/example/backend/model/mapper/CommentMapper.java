package com.example.backend.model.mapper;

import com.example.backend.model.entity.Comment;
import com.example.backend.model.entity.Post;
import com.example.backend.model.request.CommentRequest;
import com.example.backend.model.respon.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserMapper userMapper;

    public Comment toEntity(CommentRequest request) {
        if (request == null) {
            return null;
        }
        return Comment.builder()
                .content(request.getContent())
                .post(request.getPostId() != null ? Post.builder().id(request.getPostId()).build() : null)
                .comment(request.getParentCommentId() != null ? Comment.builder().id(request.getParentCommentId()).build() : null)
                .build();
    }

    public CommentResponse toResponse(Comment entity) {
        if (entity == null) {
            return null;
        }
        return CommentResponse.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .user(userMapper.toResponse(entity.getUser()))
                .postId(entity.getPost() != null ? entity.getPost().getId() : null)
                .parentCommentId(entity.getComment() != null ? entity.getComment().getId() : null)
                .createAt(entity.getCreateAt())
                .updateAt(entity.getUpdateAt())
                .build();
    }

    public CommentRequest toRequest(Comment entity) {
        if (entity == null) {
            return null;
        }
        return CommentRequest.builder()
                .content(entity.getContent())
                .postId(entity.getPost() != null ? entity.getPost().getId() : null)
                .parentCommentId(entity.getComment() != null ? entity.getComment().getId() : null)
                .build();
    }

    public Comment toEntity(CommentResponse response) {
        if (response == null) {
            return null;
        }
        return Comment.builder()
                .id(response.getId())
                .content(response.getContent())
                .user(userMapper.toEntity(response.getUser()))
                .post(response.getPostId() != null ? Post.builder().id(response.getPostId()).build() : null)
                .comment(response.getParentCommentId() != null ? Comment.builder().id(response.getParentCommentId()).build() : null)
                .createAt(response.getCreateAt())
                .updateAt(response.getUpdateAt())
                .build();
    }

    public CommentResponse toResponse(CommentRequest request) {
        if (request == null) {
            return null;
        }
        return CommentResponse.builder()
                .content(request.getContent())
                .postId(request.getPostId())
                .parentCommentId(request.getParentCommentId())
                .build();
    }
}
