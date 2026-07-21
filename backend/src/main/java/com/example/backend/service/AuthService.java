package com.example.backend.service;

import java.util.List;

import com.example.backend.model.request.Account;
import com.example.backend.model.request.LoginRequest;
import com.example.backend.model.respon.AuthResponse;
import com.example.backend.model.respon.UserResponse;

public interface AuthService {
    Boolean forgotPassword(String email);
    
    Boolean resetPassword(String email, String newPassword);
    
    // Đăng nhập — trả token + info
    AuthResponse login(LoginRequest request);

    // Đăng ký — trả info user vừa tạo
    UserResponse registerUser(Account account);

    // Xoay vòng token — trả accessToken mới
    AuthResponse refreshToken(String refreshToken);

    // Đăng xuất — đưa token vào blacklist
    void logout(String accessToken);

    // Kiểm tra user có đang đăng nhập không
    boolean isLoggedIn(Long userId);

    // Thu hồi token — Admin khóa tài khoản
    void revokeToken(Long userId);

    // Tạo 10 user giả định (seed)
    List<UserResponse> seedUsers();
}
