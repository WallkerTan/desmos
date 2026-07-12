package com.example.backend.service;

import org.springframework.data.domain.Page;
import com.example.backend.model.entity.User;
import com.example.backend.model.request.Account;
import com.example.backend.model.request.UserRequest;
import com.example.backend.model.respon.UserResponse;

public interface UserService {
    // Đổi mật khẩu

    Boolean tranPassword(Long id, String newPasword);

    // chỉnh sửa hồ sơ , xem hồ sơ ng khác
    User updateProfile(Long id, UserRequest userRequest);

    // tìm kiếm người dùng
    Page<UserResponse> findUserByName(String username);
}
