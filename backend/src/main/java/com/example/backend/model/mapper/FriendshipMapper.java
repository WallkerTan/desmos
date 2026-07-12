package com.example.backend.model.mapper;

import com.example.backend.model.entity.Friendship;
import com.example.backend.model.entity.User;
import com.example.backend.model.request.FriendshipRequest;
import com.example.backend.model.respon.FriendshipResponse;
import com.example.backend.model.respon.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendshipMapper {

    private final UserMapper userMapper;

    public Friendship toEntity(FriendshipRequest request) {
        if (request == null) {
            return null;
        }
        return Friendship.builder()
                .receiver(request.getReceiverId() != null ? User.builder().id(request.getReceiverId()).build() : null)
                .build();
    }

    public FriendshipResponse toResponse(Friendship entity) {
        if (entity == null) {
            return null;
        }
        return FriendshipResponse.builder()
                .id(entity.getId())
                .sender(userMapper.toResponse(entity.getSender()))
                .receiver(userMapper.toResponse(entity.getReceiver()))
                .createAt(entity.getCreateAt())
                .build();
    }

    public FriendshipRequest toRequest(Friendship entity) {
        if (entity == null) {
            return null;
        }
        return FriendshipRequest.builder()
                .receiverId(entity.getReceiver() != null ? entity.getReceiver().getId() : null)
                .build();
    }

    public Friendship toEntity(FriendshipResponse response) {
        if (response == null) {
            return null;
        }
        return Friendship.builder()
                .id(response.getId())
                .sender(userMapper.toEntity(response.getSender()))
                .receiver(userMapper.toEntity(response.getReceiver()))
                .createAt(response.getCreateAt())
                .build();
    }

    public FriendshipResponse toResponse(FriendshipRequest request) {
        if (request == null) {
            return null;
        }
        return FriendshipResponse.builder()
                .receiver(request.getReceiverId() != null ? UserResponse.builder().id(request.getReceiverId()).build() : null)
                .build();
    }
}
