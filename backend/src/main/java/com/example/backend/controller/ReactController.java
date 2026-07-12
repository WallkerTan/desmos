package com.example.backend.controller;

import com.example.backend.model.request.ReactRequest;
import com.example.backend.model.respon.ApiDataResponse;
import com.example.backend.model.respon.ReactResponse;
import com.example.backend.service.ReactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reacts")
@RequiredArgsConstructor
public class ReactController {

    private final ReactService reactService;

    @PostMapping
    public ResponseEntity<ApiDataResponse<ReactResponse>> reactPostOrComment(
            @RequestParam Long currentUserId,
            @RequestBody ReactRequest request) {
        ReactResponse response = reactService.reactPostOrComment(currentUserId, request);
        return ResponseEntity.ok(ApiDataResponse.<ReactResponse>builder()
                .status(true)
                .message("Reaction updated successfully")
                .data(response)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiDataResponse<List<ReactResponse>>> getReactionsByPostId(
            @PathVariable Long postId) {
        List<ReactResponse> reactions = reactService.getReactionsByPostId(postId);
        return ResponseEntity.ok(ApiDataResponse.<List<ReactResponse>>builder()
                .status(true)
                .message("Post reactions retrieved")
                .data(reactions)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<ApiDataResponse<List<ReactResponse>>> getReactionsByCommentId(
            @PathVariable Long commentId) {
        List<ReactResponse> reactions = reactService.getReactionsByCommentId(commentId);
        return ResponseEntity.ok(ApiDataResponse.<List<ReactResponse>>builder()
                .status(true)
                .message("Comment reactions retrieved")
                .data(reactions)
                .httpStatus(HttpStatus.OK)
                .build());
    }
}
