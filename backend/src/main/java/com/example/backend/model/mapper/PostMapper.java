package com.example.backend.model.mapper;

import com.example.backend.model.entity.Post;
import com.example.backend.model.request.PostRequest;
import com.example.backend.model.respon.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final UserMapper userMapper;

    public Post toEntity(PostRequest request) {
        if (request == null) {
            return null;
        }
        return Post.builder()
                .caption(request.getCaption())
                .privacy(request.getPrivacy())
                .status(request.getStatus())
                .build();
    }

    public PostResponse toResponse(Post entity) {
        if (entity == null) {
            return null;
        }
        return PostResponse.builder()
                .id(entity.getId())
                .caption(entity.getCaption())
                .user(userMapper.toResponse(entity.getUser()))
                .privacy(entity.getPrivacy())
                .status(entity.getStatus())
                .createAt(entity.getCreateAt())
                .updateAt(entity.getUpdateAt())
                .build();
    }

    public PostRequest toRequest(Post entity) {
        if (entity == null) {
            return null;
        }
        return PostRequest.builder()
                .caption(entity.getCaption())
                .privacy(entity.getPrivacy())
                .status(entity.getStatus())
                .build();
    }

    public Post toEntity(PostResponse response) {
        if (response == null) {
            return null;
        }
        return Post.builder()
                .id(response.getId())
                .caption(response.getCaption())
                .user(userMapper.toEntity(response.getUser()))
                .privacy(response.getPrivacy())
                .status(response.getStatus())
                .createAt(response.getCreateAt())
                .updateAt(response.getUpdateAt())
                .build();
    }

    public PostResponse toResponse(PostRequest request) {
        if (request == null) {
            return null;
        }
        return PostResponse.builder()
                .caption(request.getCaption())
                .privacy(request.getPrivacy())
                .status(request.getStatus())
                .build();
    }
}
