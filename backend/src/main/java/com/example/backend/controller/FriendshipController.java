package com.example.backend.controller;

import com.example.backend.model.respon.ApiDataResponse;
import com.example.backend.model.respon.FriendResponse;
import com.example.backend.model.respon.FriendshipResponse;
import com.example.backend.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<FriendResponse>>> getFriends(
            @RequestParam Long currentUserId) {
        List<FriendResponse> friends = friendshipService.getFriends(currentUserId);
        return ResponseEntity.ok(ApiDataResponse.<List<FriendResponse>>builder()
                .status(true)
                .message("Friends retrieved successfully")
                .data(friends)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @PostMapping("/requests/{receiverId}")
    public ResponseEntity<ApiDataResponse<FriendshipResponse>> sendFriendRequest(
            @RequestParam Long currentUserId,
            @PathVariable Long receiverId) {
        FriendshipResponse response = friendshipService.sendFriendRequest(currentUserId, receiverId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiDataResponse.<FriendshipResponse>builder()
                .status(true)
                .message("Friend request sent")
                .data(response)
                .httpStatus(HttpStatus.CREATED)
                .build());
    }

    @PostMapping("/requests/{senderId}/accept")
    public ResponseEntity<ApiDataResponse<FriendResponse>> acceptFriendRequest(
            @RequestParam Long currentUserId,
            @PathVariable Long senderId) {
        FriendResponse response = friendshipService.acceptFriendRequest(currentUserId, senderId);
        return ResponseEntity.ok(ApiDataResponse.<FriendResponse>builder()
                .status(true)
                .message("Friend request accepted")
                .data(response)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @PostMapping("/requests/{senderId}/reject")
    public ResponseEntity<ApiDataResponse<Boolean>> rejectFriendRequest(
            @RequestParam Long currentUserId,
            @PathVariable Long senderId) {
        Boolean result = friendshipService.rejectFriendRequest(currentUserId, senderId);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Friend request rejected")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @DeleteMapping("/requests/{receiverId}")
    public ResponseEntity<ApiDataResponse<Boolean>> cancelFriendRequest(
            @RequestParam Long currentUserId,
            @PathVariable Long receiverId) {
        Boolean result = friendshipService.cancelFriendRequest(currentUserId, receiverId);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Friend request cancelled")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<ApiDataResponse<Boolean>> unfriend(
            @RequestParam Long currentUserId,
            @PathVariable Long friendId) {
        Boolean result = friendshipService.unfriend(currentUserId, friendId);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Unfriended successfully")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @PostMapping("/{blockedId}/block")
    public ResponseEntity<ApiDataResponse<Boolean>> blockUser(
            @RequestParam Long currentUserId,
            @PathVariable Long blockedId) {
        Boolean result = friendshipService.blockUser(currentUserId, blockedId);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("User blocked successfully")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @PostMapping("/{blockedId}/unblock")
    public ResponseEntity<ApiDataResponse<Boolean>> unblockUser(
            @RequestParam Long currentUserId,
            @PathVariable Long blockedId) {
        Boolean result = friendshipService.unblockUser(currentUserId, blockedId);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("User unblocked successfully")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @GetMapping("/requests/pending")
    public ResponseEntity<ApiDataResponse<Page<FriendshipResponse>>> getPendingRequests(
            @RequestParam Long currentUserId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<FriendshipResponse> pendingRequests = friendshipService.getPendingRequests(currentUserId, page, size);
        return ResponseEntity.ok(ApiDataResponse.<Page<FriendshipResponse>>builder()
                .status(true)
                .message("Pending requests retrieved")
                .data(pendingRequests)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @GetMapping("/blocked")
    public ResponseEntity<ApiDataResponse<List<FriendResponse>>> getBlockedUsers(
            @RequestParam Long currentUserId) {
        List<FriendResponse> blockedUsers = friendshipService.getBlockedUsers(currentUserId);
        return ResponseEntity.ok(ApiDataResponse.<List<FriendResponse>>builder()
                .status(true)
                .message("Blocked users retrieved")
                .data(blockedUsers)
                .httpStatus(HttpStatus.OK)
                .build());
    }
}
