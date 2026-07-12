package com.example.backend.model.mapper;

import com.example.backend.model.entity.Post;
import com.example.backend.model.entity.PostMedia;
import com.example.backend.model.request.PostMediaRequest;
import com.example.backend.model.respon.PostMediaResponse;
import org.springframework.stereotype.Component;

@Component
public class PostMediaMapper {

    public PostMedia toEntity(PostMediaRequest request) {
        if (request == null) {
            return null;
        }
        return PostMedia.builder()
                .post(request.getPostId() != null ? Post.builder().id(request.getPostId()).build() : null)
                .mediaUrl(request.getMediaUrl())
                .mediaType(request.getMediaType())
                .build();
    }

    public PostMediaResponse toResponse(PostMedia entity) {
        if (entity == null) {
            return null;
        }
        return PostMediaResponse.builder()
                .id(entity.getId())
                .postId(entity.getPost() != null ? entity.getPost().getId() : null)
                .mediaUrl(entity.getMediaUrl())
                .mediaType(entity.getMediaType())
                .createAt(entity.getCreateAt())
                .updateAt(entity.getUpdateAt())
                .build();
    }

    public PostMediaRequest toRequest(PostMedia entity) {
        if (entity == null) {
            return null;
        }
        return PostMediaRequest.builder()
                .postId(entity.getPost() != null ? entity.getPost().getId() : null)
                .mediaUrl(entity.getMediaUrl())
                .mediaType(entity.getMediaType())
                .build();
    }

    public PostMedia toEntity(PostMediaResponse response) {
        if (response == null) {
            return null;
        }
        return PostMedia.builder()
                .id(response.getId())
                .post(response.getPostId() != null ? Post.builder().id(response.getPostId()).build() : null)
                .mediaUrl(response.getMediaUrl())
                .mediaType(response.getMediaType())
                .createAt(response.getCreateAt())
                .updateAt(response.getUpdateAt())
                .build();
    }

    public PostMediaResponse toResponse(PostMediaRequest request) {
        if (request == null) {
            return null;
        }
        return PostMediaResponse.builder()
                .postId(request.getPostId())
                .mediaUrl(request.getMediaUrl())
                .mediaType(request.getMediaType())
                .build();
    }
}
