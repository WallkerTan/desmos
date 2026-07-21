package com.example.backend.controller;

import com.example.backend.model.request.Account;
import com.example.backend.model.request.LoginRequest;
import com.example.backend.model.respon.ApiDataResponse;
import com.example.backend.model.respon.AuthResponse;
import com.example.backend.model.respon.UserResponse;
import com.example.backend.security.JwtUtil;
import com.example.backend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ApiDataResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiDataResponse.<AuthResponse>builder()
                .status(true)
                .message("Login successful")
                .data(response)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<ApiDataResponse<UserResponse>> register(@Valid @RequestBody Account account) {
        UserResponse response = authService.registerUser(account);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiDataResponse.<UserResponse>builder()
                        .status(true)
                        .message("Registration successful")
                        .data(response)
                        .httpStatus(HttpStatus.CREATED)
                        .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiDataResponse<Void>> logout(HttpServletRequest request) {
        String token = jwtUtil.getTokenFromRequest(request);
        if (token != null) {
            authService.logout(token);
        }
        return ResponseEntity.ok(ApiDataResponse.<Void>builder()
                .status(true)
                .message("Logout successful")
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiDataResponse<AuthResponse>> refresh(@RequestParam String refreshToken) {
        AuthResponse response = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(ApiDataResponse.<AuthResponse>builder()
                .status(true)
                .message("Token refreshed successfully")
                .data(response)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiDataResponse<Boolean>> forgotPassword(@RequestParam String email) {
        Boolean result = authService.forgotPassword(email);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Forgot password email sent")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiDataResponse<Boolean>> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        Boolean result = authService.resetPassword(email, newPassword);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Password reset successful")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @PostMapping("/seed")
    public ResponseEntity<ApiDataResponse<java.util.List<UserResponse>>> seedUsers() {
        java.util.List<UserResponse> users = authService.seedUsers();
        return ResponseEntity.ok(ApiDataResponse.<java.util.List<UserResponse>>builder()
                .status(true)
                .message("Seeded 10 users successfully")
                .data(users)
                .httpStatus(HttpStatus.OK)
                .build());
    }
}
