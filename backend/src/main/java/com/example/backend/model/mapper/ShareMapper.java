package com.example.backend.model.mapper;

import com.example.backend.model.entity.Post;
import com.example.backend.model.entity.Share;
import com.example.backend.model.entity.User;
import com.example.backend.model.request.ShareRequest;
import com.example.backend.model.respon.PostResponse;
import com.example.backend.model.respon.ShareResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShareMapper {

    private final UserMapper userMapper;
    private final PostMapper postMapper;

    public Share toEntity(ShareRequest request) {
        if (request == null) {
            return null;
        }
        return Share.builder()
                .caption(request.getCaption())
                .post(request.getPostId() != null ? Post.builder().id(request.getPostId()).build() : null)
                .build();
    }

    public ShareResponse toResponse(Share entity) {
        if (entity == null) {
            return null;
        }
        return ShareResponse.builder()
                .id(entity.getId())
                .caption(entity.getCaption())
                .user(userMapper.toResponse(entity.getUser()))
                .post(postMapper.toResponse(entity.getPost()))
                .createAt(entity.getCreateAt())
                .updateAt(entity.getUpdateAt())
                .build();
    }

    public ShareRequest toRequest(Share entity) {
        if (entity == null) {
            return null;
        }
        return ShareRequest.builder()
                .caption(entity.getCaption())
                .postId(entity.getPost() != null ? entity.getPost().getId() : null)
                .build();
    }

    public Share toEntity(ShareResponse response) {
        if (response == null) {
            return null;
        }
        return Share.builder()
                .id(response.getId())
                .caption(response.getCaption())
                .user(userMapper.toEntity(response.getUser()))
                .post(postMapper.toEntity(response.getPost()))
                .createAt(response.getCreateAt())
                .updateAt(response.getUpdateAt())
                .build();
    }

    public ShareResponse toResponse(ShareRequest request) {
        if (request == null) {
            return null;
        }
        return ShareResponse.builder()
                .caption(request.getCaption())
                .post(request.getPostId() != null ? PostResponse.builder().id(request.getPostId()).build() : null)
                .build();
    }
}
