package com.example.backend.model.mapper;

import com.example.backend.model.entity.User;
import com.example.backend.model.request.UserRequest;
import com.example.backend.model.respon.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest request) {
        if (request == null) {
            return null;
        }
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(request.getPassword())
                .avata(request.getAvata())
                .coverPhoto(request.getCoverPhoto())
                .bio(request.getBio())
                .dod(request.getDod())
                .gender(request.getGender())
                .role(request.getRole())
                .build();
    }

    public UserResponse toResponse(User entity) {
        if (entity == null) {
            return null;
        }
        return UserResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .avata(entity.getAvata())
                .coverPhoto(entity.getCoverPhoto())
                .bio(entity.getBio())
                .dod(entity.getDod())
                .gender(entity.getGender())
                .role(entity.getRole())
                .isVerified(entity.getIsVerified())
                .isActive(entity.getIsActive())
                .createAt(entity.getCreateAt())
                .updateAt(entity.getUpdateAt())
                .build();
    }

    public UserRequest toRequest(User entity) {
        if (entity == null) {
            return null;
        }
        return UserRequest.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .password(entity.getPassword())
                .avata(entity.getAvata())
                .coverPhoto(entity.getCoverPhoto())
                .bio(entity.getBio())
                .dod(entity.getDod())
                .gender(entity.getGender())
                .role(entity.getRole())
                .build();
    }

    public User toEntity(UserResponse response) {
        if (response == null) {
            return null;
        }
        return User.builder()
                .id(response.getId())
                .username(response.getUsername())
                .email(response.getEmail())
                .phone(response.getPhone())
                .avata(response.getAvata())
                .coverPhoto(response.getCoverPhoto())
                .bio(response.getBio())
                .dod(response.getDod())
                .gender(response.getGender())
                .role(response.getRole())
                .isVerified(response.getIsVerified())
                .isActive(response.getIsActive())
                .createAt(response.getCreateAt())
                .updateAt(response.getUpdateAt())
                .build();
    }

    public UserResponse toResponse(UserRequest request) {
        if (request == null) {
            return null;
        }
        return UserResponse.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .avata(request.getAvata())
                .coverPhoto(request.getCoverPhoto())
                .bio(request.getBio())
                .dod(request.getDod())
                .gender(request.getGender())
                .role(request.getRole())
                .build();
    }
}
