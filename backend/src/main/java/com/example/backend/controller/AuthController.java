package com.example.backend.controller;

import com.example.backend.model.entity.User;
import com.example.backend.model.request.Account;
import com.example.backend.model.request.LoginRequest;
import com.example.backend.model.respon.ApiDataResponse;
import com.example.backend.service.AuthService;
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

    @PostMapping("/login")
    public ResponseEntity<ApiDataResponse<String>> login(@Valid @RequestBody LoginRequest request) {
        String tokenOrMessage = authService.login(request);
        return ResponseEntity.ok(ApiDataResponse.<String>builder()
                .status(true)
                .message("Login successful")
                .data(tokenOrMessage)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<ApiDataResponse<User>> register(@Valid @RequestBody Account account) {
        User user = authService.register(account);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiDataResponse.<User>builder()
                        .status(true)
                        .message("Registration successful")
                        .data(user)
                        .httpStatus(HttpStatus.CREATED)
                        .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiDataResponse<Boolean>> logout() {
        Boolean result = authService.logout();
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Logout successful")
                .data(result)
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
}
