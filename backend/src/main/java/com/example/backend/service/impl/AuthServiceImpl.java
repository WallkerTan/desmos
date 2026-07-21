package com.example.backend.service.impl;

import com.example.backend.exception.BadRequestException;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.e_security.RefreshToken;
import com.example.backend.model.e_security.TokenBlackList;
import com.example.backend.model.entity.User;
import com.example.backend.model.enums.Role;
import com.example.backend.model.request.Account;
import com.example.backend.model.request.LoginRequest;
import com.example.backend.model.respon.AuthResponse;
import com.example.backend.model.respon.UserResponse;
import com.example.backend.model.mapper.UserMapper;
import com.example.backend.repository.UserReposetory;
import com.example.backend.repository.r_security.RefreshTokenRepository;
import com.example.backend.repository.r_security.TokenBlackListRepository;
import com.example.backend.security.JwtUtil;
import com.example.backend.security.UserDetail;
import com.example.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserReposetory userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenBlackListRepository tokenBlacklistRepository;
    private final UserMapper userMapper;

    @Value("${jwt.refresh-expiration}")
    private long refreshTokenExpiration;

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Tài khoản không tồn tại"));

        if (user.getIsActive() != null && !user.getIsActive()) {
            throw new BadRequestException("Tài khoản đã bị vô hiệu hóa");
        }

        if (user.getLockUntil() != null && user.getLockUntil().after(new java.sql.Timestamp(System.currentTimeMillis()))) {
            throw new BadRequestException("Tài khoản đang bị khóa tạm thời. Vui lòng thử lại sau.");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            int attempts = user.getFalseLogin() != null ? user.getFalseLogin() + 1 : 1;
            user.setFalseLogin(attempts);
            if (attempts >= 5) {
                user.setLockUntil(new java.sql.Timestamp(System.currentTimeMillis() + 15 * 60 * 1000));
                userRepository.save(user);
                throw new BadRequestException("Tài khoản đã bị khóa 15 phút do sai mật khẩu quá 5 lần");
            }
            userRepository.save(user);
            throw new BadRequestException("Mật khẩu không chính xác");
        }

        // Reset false login attempts if needed
        if (user.getFalseLogin() != null && user.getFalseLogin() > 0) {
            user.setFalseLogin(0);
            user.setLockUntil(null);
            userRepository.save(user);
        }

        UserDetails userDetails = new UserDetail(user);
        String accessToken = jwtUtil.generateAccessToken(userDetails);
        RefreshToken refreshTokenEntity = createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenEntity.getToken())
                .user(userMapper.toResponse(user))
                .build();
    }

    @Override
    @Transactional
    public UserResponse registerUser(Account account) {
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

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(String refreshToken) {
        RefreshToken tokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new BadRequestException("Refresh token không hợp lệ"));

        if (tokenEntity.isRevoke()) {
            throw new BadRequestException("Refresh token đã bị thu hồi");
        }

        if (tokenEntity.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(tokenEntity);
            throw new BadRequestException("Refresh token đã hết hạn. Vui lòng đăng nhập lại");
        }

        User user = tokenEntity.getUser();
        UserDetails userDetails = new UserDetail(user);
        String newAccessToken = jwtUtil.generateAccessToken(userDetails);

        // Rotate refresh token
        refreshTokenRepository.delete(tokenEntity);
        RefreshToken newRefreshTokenEntity = createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshTokenEntity.getToken())
                .user(userMapper.toResponse(user))
                .build();
    }

    @Override
    @Transactional
    public void logout(String accessToken) {
        if (accessToken == null) {
            return;
        }

        try {
            String username = jwtUtil.extractUsername(accessToken);
            java.util.Date expiration = jwtUtil.extractExpiration(accessToken);
            LocalDateTime expiredAt = expiration.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            User user = userRepository.findByUsername(username).orElse(null);

            // Add token to blacklist
            TokenBlackList blacklistEntry = TokenBlackList.builder()
                    .token(accessToken)
                    .expiredAt(expiredAt)
                    .user(user)
                    .build();
            tokenBlacklistRepository.save(blacklistEntry);

            // Delete user's refresh tokens
            if (user != null) {
                List<RefreshToken> tokens = refreshTokenRepository.findAllByUserId(user.getId());
                refreshTokenRepository.deleteAll(tokens);
            }
        } catch (Exception e) {
            // Token is already expired or invalid — ignore
        }
    }

    @Override
    public boolean isLoggedIn(Long userId) {
        List<RefreshToken> tokens = refreshTokenRepository.findAllByUserId(userId);
        return tokens.stream().anyMatch(token -> !token.isRevoke() && token.getExpiryDate().isAfter(Instant.now()));
    }

    @Override
    @Transactional
    public void revokeToken(Long userId) {
        List<RefreshToken> tokens = refreshTokenRepository.findAllByUserId(userId);
        refreshTokenRepository.deleteAll(tokens);
        tokenBlacklistRepository.deleteAllByUserId(userId);
    }

    @Override
    public Boolean forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với email này"));
        return true;
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

    private RefreshToken createRefreshToken(User user) {
        // Clear old user tokens first
        List<RefreshToken> existing = refreshTokenRepository.findAllByUserId(user.getId());
        if (!existing.isEmpty()) {
            refreshTokenRepository.deleteAll(existing);
        }

        UserDetails userDetails = new UserDetail(user);
        String tokenString = jwtUtil.generateRefreshToken(userDetails);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(tokenString)
                .expiryDate(Instant.now().plusMillis(refreshTokenExpiration))
                .revoke(false)
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional
    public List<UserResponse> seedUsers() {
        List<UserResponse> responses = new java.util.ArrayList<>();
        String defaultPassword = passwordEncoder.encode("12345678aA.");

        for (int i = 1; i <= 10; i++) {
            String username = "testuser" + i;
            String email = "testuser" + i + "@example.com";

            // Bỏ qua nếu đã tồn tại
            if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
                userRepository.findByUsername(username).ifPresent(user -> responses.add(userMapper.toResponse(user)));
                continue;
            }

            User user = User.builder()
                    .username(username)
                    .email(email)
                    .phone("012345678" + (i % 10)) // random digit at the end to make it a bit unique
                    .password(defaultPassword)
                    .role(Role.user)
                    .isActive(true)
                    .isVerified(true)
                    .falseLogin(0)
                    .createAt(LocalDateTime.now())
                    .build();

            User savedUser = userRepository.save(user);
            responses.add(userMapper.toResponse(savedUser));
        }

        return responses;
    }
}
