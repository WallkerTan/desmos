package com.example.backend.service.impl;

import com.example.backend.exception.BadRequestException;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.entity.User;
import com.example.backend.model.enums.Role;
import com.example.backend.model.request.Account;
import com.example.backend.model.request.LoginRequest;
import com.example.backend.repository.UserReposetory;
import com.example.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserReposetory userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Tài khoản không tồn tại"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Mật khẩu không chính xác");
        }

        if (!user.getIsActive()) {
            throw new BadRequestException("Tài khoản đã bị khóa");
        }

        // Tạm thời trả về chuỗi thành công. Khi tích hợp JWT, trả về Token tại đây.
        return "Đăng nhập thành công! (Cần tích hợp JWT Token)";
    }

    @Override
    @Transactional
    public User register(Account account) {
        if (account == null) {
            throw new BadRequestException("Dữ liệu đăng ký không hợp lệ");
        }
        if (userRepository.existsByUsername(account.getUsername())) {
            throw new BadRequestException("Username đã được sử dụng");
        }
        if (userRepository.existsByEmail(account.getEmail())) {
            throw new BadRequestException("Email đã được sử dụng");
        }

        User user = User.builder()
                .username(account.getUsername())
                .email(account.getEmail())
                .phone(account.getPhone())
                .password(passwordEncoder.encode(account.getPassword()))
                .role(Role.user)
                .isActive(true)
                .isVerified(false)
                .falseLogin(0)
                .createAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

    @Override
    public Boolean logout() {
        // Tạm thời trả về true. Khi tích hợp Token, có thể xử lý blacklist token tại đây.
        return true;
    }

    @Override
    public Boolean forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với email này"));

        // Logic gửi email kèm mã xác nhận hoặc link đổi mật khẩu sẽ nằm ở đây
        // VD: emailService.sendResetPasswordEmail(user.getEmail(), token);
        
        return true; // Trả về true biểu thị đã gửi email
    }

    @Override
    @Transactional
    public Boolean resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với email này"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdateAt(LocalDateTime.now());
        userRepository.save(user);
        
        return true;
    }
}
