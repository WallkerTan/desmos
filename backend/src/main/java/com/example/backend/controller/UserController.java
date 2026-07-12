package com.example.backend.controller;

import com.example.backend.model.entity.User;
import com.example.backend.model.request.UserRequest;
import com.example.backend.model.respon.ApiDataResponse;
import com.example.backend.model.respon.UserResponse;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/me/password")
    public ResponseEntity<ApiDataResponse<Boolean>> changePassword(
            @RequestParam Long currentUserId,
            @RequestParam String newPassword) {
        Boolean result = userService.tranPassword(currentUserId, newPassword);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Password changed successfully")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @PutMapping("/me")
    public ResponseEntity<ApiDataResponse<User>> updateProfile(
            @RequestParam Long currentUserId,
            @RequestBody UserRequest request) {
        User user = userService.updateProfile(currentUserId, request);
        return ResponseEntity.ok(ApiDataResponse.<User>builder()
                .status(true)
                .message("Profile updated successfully")
                .data(user)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiDataResponse<Page<UserResponse>>> searchUsers(
            @RequestParam String username) {
        Page<UserResponse> users = userService.findUserByName(username);
        return ResponseEntity.ok(ApiDataResponse.<Page<UserResponse>>builder()
                .status(true)
                .message("Search results retrieved")
                .data(users)
                .httpStatus(HttpStatus.OK)
                .build());
    }
}
