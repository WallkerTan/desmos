package com.example.backend.model.mapper;

import com.example.backend.model.entity.Friend;
import com.example.backend.model.entity.User;
import com.example.backend.model.request.FriendRequest;
import com.example.backend.model.respon.FriendResponse;
import com.example.backend.model.respon.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendMapper {

    private final UserMapper userMapper;

    public Friend toEntity(FriendRequest request) {
        if (request == null) {
            return null;
        }
        return Friend.builder()
                .user2(request.getUser2Id() != null ? User.builder().id(request.getUser2Id()).build() : null)
                .status(request.getStatus())
                .build();
    }

    public FriendResponse toResponse(Friend entity) {
        if (entity == null) {
            return null;
        }
        return FriendResponse.builder()
                .id(entity.getId())
                .user1(userMapper.toResponse(entity.getUser1()))
                .user2(userMapper.toResponse(entity.getUser2()))
                .status(entity.getStatus())
                .createAt(entity.getCreateAt())
                .updateAt(entity.getUpdateAt())
                .build();
    }

    public FriendRequest toRequest(Friend entity) {
        if (entity == null) {
            return null;
        }
        return FriendRequest.builder()
                .user2Id(entity.getUser2() != null ? entity.getUser2().getId() : null)
                .status(entity.getStatus())
                .build();
    }

    public Friend toEntity(FriendResponse response) {
        if (response == null) {
            return null;
        }
        return Friend.builder()
                .id(response.getId())
                .user1(userMapper.toEntity(response.getUser1()))
                .user2(userMapper.toEntity(response.getUser2()))
                .status(response.getStatus())
                .createAt(response.getCreateAt())
                .updateAt(response.getUpdateAt())
                .build();
    }

    public FriendResponse toResponse(FriendRequest request) {
        if (request == null) {
            return null;
        }
        return FriendResponse.builder()
                .user2(request.getUser2Id() != null ? UserResponse.builder().id(request.getUser2Id()).build() : null)
                .status(request.getStatus())
                .build();
    }
}
