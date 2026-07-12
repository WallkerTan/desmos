package com.example.backend.controller;

import com.example.backend.model.entity.Post;
import com.example.backend.model.request.PostRequest;
import com.example.backend.model.respon.ApiDataResponse;
import com.example.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiDataResponse<Post>> createPost(
            @RequestBody PostRequest postRequest) {
        Post post = postService.createPost(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiDataResponse.<Post>builder()
                .status(true)
                .message("Post created successfully")
                .data(post)
                .httpStatus(HttpStatus.CREATED)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Post>> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(ApiDataResponse.<Post>builder()
                .status(true)
                .message("Post retrieved successfully")
                .data(post)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiDataResponse<Page<Post>>> searchPosts(
            @RequestParam String caption,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<Post> posts = postService.findByCaption(caption, PageRequest.of(page, size));
        return ResponseEntity.ok(ApiDataResponse.<Page<Post>>builder()
                .status(true)
                .message("Posts retrieved successfully")
                .data(posts)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Boolean>> updatePost(
            @PathVariable Long id,
            @RequestBody PostRequest postRequest) {
        Boolean result = postService.updatePost(id, postRequest);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Post updated successfully")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Boolean>> deletePost(@PathVariable Long id) {
        Boolean result = postService.deletePost(id);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Post deleted successfully")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }
}
