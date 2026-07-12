package com.example.backend.controller;

import com.example.backend.model.enums.PostStatus;
import com.example.backend.model.enums.Role;
import com.example.backend.model.respon.ApiDataResponse;
import com.example.backend.model.respon.PostResponse;
import com.example.backend.model.respon.UserResponse;
import com.example.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // --- QUẢN LÝ NGƯỜI DÙNG ---

    @GetMapping("/users")
    public ResponseEntity<ApiDataResponse<Page<UserResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<UserResponse> users = adminService.getAllUsers(PageRequest.of(page, size));
        return ResponseEntity.ok(ApiDataResponse.<Page<UserResponse>>builder()
                .status(true).message("Users retrieved").data(users).httpStatus(HttpStatus.OK).build());
    }

    @GetMapping("/users/filter/active")
    public ResponseEntity<ApiDataResponse<Page<UserResponse>>> filterUsersByActive(
            @RequestParam Boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<UserResponse> users = adminService.filterUsersByActive(isActive, PageRequest.of(page, size));
        return ResponseEntity.ok(ApiDataResponse.<Page<UserResponse>>builder()
                .status(true).message("Filtered users retrieved").data(users).httpStatus(HttpStatus.OK).build());
    }

    @GetMapping("/users/filter/role")
    public ResponseEntity<ApiDataResponse<Page<UserResponse>>> filterUsersByRole(
            @RequestParam Role role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<UserResponse> users = adminService.filterUsersByRole(role, PageRequest.of(page, size));
        return ResponseEntity.ok(ApiDataResponse.<Page<UserResponse>>builder()
                .status(true).message("Filtered users retrieved").data(users).httpStatus(HttpStatus.OK).build());
    }

    @PutMapping("/users/{userId}/status")
    public ResponseEntity<ApiDataResponse<Boolean>> lockOrUnlockUser(
            @PathVariable Long userId,
            @RequestParam Boolean isActive) {
        Boolean result = adminService.lockOrUnlockUser(userId, isActive);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true).message("User status updated").data(result).httpStatus(HttpStatus.OK).build());
    }

    // --- QUẢN LÝ BÀI VIẾT ---

    @GetMapping("/posts")
    public ResponseEntity<ApiDataResponse<Page<PostResponse>>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<PostResponse> posts = adminService.getAllPosts(PageRequest.of(page, size));
        return ResponseEntity.ok(ApiDataResponse.<Page<PostResponse>>builder()
                .status(true).message("Posts retrieved").data(posts).httpStatus(HttpStatus.OK).build());
    }

    @GetMapping("/posts/filter/status")
    public ResponseEntity<ApiDataResponse<Page<PostResponse>>> filterPostsByStatus(
            @RequestParam PostStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<PostResponse> posts = adminService.filterPostsByStatus(status, PageRequest.of(page, size));
        return ResponseEntity.ok(ApiDataResponse.<Page<PostResponse>>builder()
                .status(true).message("Filtered posts retrieved").data(posts).httpStatus(HttpStatus.OK).build());
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiDataResponse<Boolean>> deletePost(
            @PathVariable Long postId) {
        Boolean result = adminService.deletePost(postId);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true).message("Post deleted by admin").data(result).httpStatus(HttpStatus.OK).build());
    }

    // --- THỐNG KÊ ---

    @GetMapping("/statistics")
    public ResponseEntity<ApiDataResponse<Map<String, Long>>> getStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        Map<String, Long> stats = new HashMap<>();
        stats.put("newUsers", adminService.countNewUsers(startDate, endDate));
        stats.put("activeUsers", adminService.countActiveUsers()); // không phụ thuộc date
        stats.put("newPosts", adminService.countNewPosts(startDate, endDate));
        stats.put("newComments", adminService.countNewComments(startDate, endDate));
        stats.put("newReacts", adminService.countNewReacts(startDate, endDate));

        return ResponseEntity.ok(ApiDataResponse.<Map<String, Long>>builder()
                .status(true).message("Statistics retrieved").data(stats).httpStatus(HttpStatus.OK).build());
    }
}
