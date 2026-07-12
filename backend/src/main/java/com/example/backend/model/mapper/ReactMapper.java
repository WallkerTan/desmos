package com.example.backend.model.mapper;

import com.example.backend.model.entity.Comment;
import com.example.backend.model.entity.Post;
import com.example.backend.model.entity.React;
import com.example.backend.model.request.ReactRequest;
import com.example.backend.model.respon.ReactResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReactMapper {

    private final UserMapper userMapper;

    public React toEntity(ReactRequest request) {
        if (request == null) {
            return null;
        }
        return React.builder()
                .reaction(request.getReaction())
                .post(request.getPostId() != null ? Post.builder().id(request.getPostId()).build() : null)
                .comment(request.getCommentId() != null ? Comment.builder().id(request.getCommentId()).build() : null)
                .build();
    }

    public ReactResponse toResponse(React entity) {
        if (entity == null) {
            return null;
        }
        return ReactResponse.builder()
                .id(entity.getId())
                .reaction(entity.getReaction())
                .user(userMapper.toResponse(entity.getUser()))
                .postId(entity.getPost() != null ? entity.getPost().getId() : null)
                .commentId(entity.getComment() != null ? entity.getComment().getId() : null)
                .createAt(entity.getCreateAt())
                .build();
    }

    public ReactRequest toRequest(React entity) {
        if (entity == null) {
            return null;
        }
        return ReactRequest.builder()
                .reaction(entity.getReaction())
                .postId(entity.getPost() != null ? entity.getPost().getId() : null)
                .commentId(entity.getComment() != null ? entity.getComment().getId() : null)
                .build();
    }

    public React toEntity(ReactResponse response) {
        if (response == null) {
            return null;
        }
        return React.builder()
                .id(response.getId())
                .reaction(response.getReaction())
                .user(userMapper.toEntity(response.getUser()))
                .post(response.getPostId() != null ? Post.builder().id(response.getPostId()).build() : null)
                .comment(response.getCommentId() != null ? Comment.builder().id(response.getCommentId()).build() : null)
                .createAt(response.getCreateAt())
                .build();
    }

    public ReactResponse toResponse(ReactRequest request) {
        if (request == null) {
            return null;
        }
        return ReactResponse.builder()
                .reaction(request.getReaction())
                .postId(request.getPostId())
                .commentId(request.getCommentId())
                .build();
    }
}
