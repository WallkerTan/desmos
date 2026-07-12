package com.example.backend.controller;

import com.example.backend.model.respon.ApiDataResponse;
import com.example.backend.model.respon.UserResponse;
import com.example.backend.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followingId}")
    public ResponseEntity<ApiDataResponse<Boolean>> followUser(
            @RequestParam Long currentUserId,
            @PathVariable Long followingId) {
        Boolean result = followService.followUser(currentUserId, followingId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Followed user successfully")
                .data(result)
                .httpStatus(HttpStatus.CREATED)
                .build());
    }

    @DeleteMapping("/{followingId}")
    public ResponseEntity<ApiDataResponse<Boolean>> unfollowUser(
            @RequestParam Long currentUserId,
            @PathVariable Long followingId) {
        Boolean result = followService.unfollowUser(currentUserId, followingId);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Unfollowed user successfully")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @GetMapping("/followers")
    public ResponseEntity<ApiDataResponse<Page<UserResponse>>> getFollowers(
            @RequestParam Long currentUserId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<UserResponse> followers = followService.getFollowers(currentUserId, page, size);
        return ResponseEntity.ok(ApiDataResponse.<Page<UserResponse>>builder()
                .status(true)
                .message("Followers retrieved")
                .data(followers)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @GetMapping("/following")
    public ResponseEntity<ApiDataResponse<Page<UserResponse>>> getFollowing(
            @RequestParam Long currentUserId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<UserResponse> following = followService.getFollowing(currentUserId, page, size);
        return ResponseEntity.ok(ApiDataResponse.<Page<UserResponse>>builder()
                .status(true)
                .message("Following retrieved")
                .data(following)
                .httpStatus(HttpStatus.OK)
                .build());
    }
}
