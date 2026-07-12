package com.example.backend.service.impl;

import com.example.backend.model.entity.User;
import com.example.backend.model.enums.Role;
import com.example.backend.model.request.UserRequest;
import com.example.backend.model.respon.UserResponse;
import com.example.backend.model.mapper.UserMapper;
import com.example.backend.repository.UserReposetory;
import com.example.backend.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserReposetory userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // Đổi mật khẩu người dùng
    @Override
    @Transactional
    public Boolean tranPassword(Long id, String newPassword) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.updatePasswordById(id, passwordEncoder.encode(newPassword));
        return true;
    }

    // Chỉnh sửa hồ sơ cá nhân
    @Override
    @Transactional
    public User updateProfile(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
        user.setAvata(userRequest.getAvata());
        user.setCoverPhoto(userRequest.getCoverPhoto());
        user.setBio(userRequest.getBio());
        user.setDod(userRequest.getDod());
        user.setGender(userRequest.getGender());
        user.setRole(userRequest.getRole());
        user.setUpdateAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    // Tìm kiếm người dùng theo tên
    @Override
    public Page<UserResponse> findUserByName(String username) {
        return userRepository.searchByKeyword(username, PageRequest.of(0, 20))
                .map(userMapper::toResponse);
    }
}
