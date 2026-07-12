package com.example.backend.model.mapper;

import com.example.backend.model.entity.Hastag;
import com.example.backend.model.entity.Post;
import com.example.backend.model.request.HastagRequest;
import com.example.backend.model.respon.HastagResponse;
import org.springframework.stereotype.Component;

@Component
public class HastagMapper {

    public Hastag toEntity(HastagRequest request) {
        if (request == null) {
            return null;
        }
        return Hastag.builder()
                .type(request.getType())
                .post(request.getPostId() != null ? Post.builder().id(request.getPostId()).build() : null)
                .build();
    }

    public HastagResponse toResponse(Hastag entity) {
        if (entity == null) {
            return null;
        }
        return HastagResponse.builder()
                .id(entity.getId())
                .type(entity.getType())
                .postId(entity.getPost() != null ? entity.getPost().getId() : null)
                .build();
    }

    public HastagRequest toRequest(Hastag entity) {
        if (entity == null) {
            return null;
        }
        return HastagRequest.builder()
                .type(entity.getType())
                .postId(entity.getPost() != null ? entity.getPost().getId() : null)
                .build();
    }

    public Hastag toEntity(HastagResponse response) {
        if (response == null) {
            return null;
        }
        return Hastag.builder()
                .id(response.getId())
                .type(response.getType())
                .post(response.getPostId() != null ? Post.builder().id(response.getPostId()).build() : null)
                .build();
    }

    public HastagResponse toResponse(HastagRequest request) {
        if (request == null) {
            return null;
        }
        return HastagResponse.builder()
                .type(request.getType())
                .postId(request.getPostId())
                .build();
    }
}
