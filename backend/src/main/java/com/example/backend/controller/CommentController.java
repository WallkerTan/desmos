package com.example.backend.controller;

import com.example.backend.model.request.CommentRequest;
import com.example.backend.model.respon.ApiDataResponse;
import com.example.backend.model.respon.CommentResponse;
import com.example.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiDataResponse<CommentResponse>> createComment(
            @RequestParam Long currentUserId,
            @RequestBody CommentRequest request) {
        CommentResponse response = commentService.createComment(currentUserId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiDataResponse.<CommentResponse>builder()
                .status(true)
                .message("Comment created successfully")
                .data(response)
                .httpStatus(HttpStatus.CREATED)
                .build());
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiDataResponse<CommentResponse>> updateComment(
            @RequestParam Long currentUserId,
            @PathVariable Long commentId,
            @RequestBody String content) {
        CommentResponse response = commentService.updateComment(currentUserId, commentId, content);
        return ResponseEntity.ok(ApiDataResponse.<CommentResponse>builder()
                .status(true)
                .message("Comment updated successfully")
                .data(response)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiDataResponse<Boolean>> deleteComment(
            @RequestParam Long currentUserId,
            @PathVariable Long commentId) {
        Boolean result = commentService.deleteComment(currentUserId, commentId);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Comment deleted successfully")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiDataResponse<Page<CommentResponse>>> getCommentsByPostId(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<CommentResponse> comments = commentService.getCommentsByPostId(postId, page, size);
        return ResponseEntity.ok(ApiDataResponse.<Page<CommentResponse>>builder()
                .status(true)
                .message("Comments retrieved successfully")
                .data(comments)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @GetMapping("/{commentId}/replies")
    public ResponseEntity<ApiDataResponse<Page<CommentResponse>>> getRepliesByCommentId(
            @PathVariable Long commentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<CommentResponse> replies = commentService.getRepliesByCommentId(commentId, page, size);
        return ResponseEntity.ok(ApiDataResponse.<Page<CommentResponse>>builder()
                .status(true)
                .message("Replies retrieved successfully")
                .data(replies)
                .httpStatus(HttpStatus.OK)
                .build());
    }
}
