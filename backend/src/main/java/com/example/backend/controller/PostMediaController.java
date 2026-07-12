package com.example.backend.controller;

import com.example.backend.model.entity.PostMedia;
import com.example.backend.model.request.PostMediaRequest;
import com.example.backend.model.respon.ApiDataResponse;
import com.example.backend.model.respon.PostMediaResponse;
import com.example.backend.service.PostMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post-medias")
@RequiredArgsConstructor
public class PostMediaController {

    private final PostMediaService postMediaService;

    @PostMapping
    public ResponseEntity<ApiDataResponse<PostMedia>> createMedia(
            @RequestBody PostMediaRequest postMediaRequest) {
        PostMedia media = postMediaService.createMedia(postMediaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiDataResponse.<PostMedia>builder()
                .status(true)
                .message("Media created successfully")
                .data(media)
                .httpStatus(HttpStatus.CREATED)
                .build());
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiDataResponse<Page<PostMediaResponse>>> getMediaByPostId(
            @PathVariable Long postId) {
        Page<PostMediaResponse> media = postMediaService.getAllByIdPost(postId);
        return ResponseEntity.ok(ApiDataResponse.<Page<PostMediaResponse>>builder()
                .status(true)
                .message("Media retrieved successfully")
                .data(media)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Boolean>> deleteMedia(@PathVariable Long id) {
        Boolean result = postMediaService.deleteById(id);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Media deleted successfully")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<ApiDataResponse<Boolean>> downloadMedia(@PathVariable Long id) {
        Boolean result = postMediaService.DowloadMedia(id);
        return ResponseEntity.ok(ApiDataResponse.<Boolean>builder()
                .status(true)
                .message("Media downloaded successfully")
                .data(result)
                .httpStatus(HttpStatus.OK)
                .build());
    }
}
