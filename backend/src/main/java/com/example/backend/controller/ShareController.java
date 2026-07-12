package com.example.backend.controller;

import com.example.backend.model.request.ShareRequest;
import com.example.backend.model.respon.ApiDataResponse;
import com.example.backend.model.respon.ShareResponse;
import com.example.backend.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shares")
@RequiredArgsConstructor
public class ShareController {

    private final ShareService shareService;

    @PostMapping
    public ResponseEntity<ApiDataResponse<ShareResponse>> sharePost(
            @RequestParam Long currentUserId,
            @RequestBody ShareRequest request) {
        ShareResponse response = shareService.sharePost(currentUserId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiDataResponse.<ShareResponse>builder()
                .status(true)
                .message("Post shared successfully")
                .data(response)
                .httpStatus(HttpStatus.CREATED)
                .build());
    }

    @DeleteMapping("/{shareId}")
    public ResponseEntity<ApiDataResponse<Boolean>> deleteShare(
            @RequestParam Long currentUserId,
            @PathVariable Long shareId) {
        Boolean result = shareService.deleteShare(currentUserId, shareId);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Shared post deleted")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }
}
