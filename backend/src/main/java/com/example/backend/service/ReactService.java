package com.example.backend.service;

import com.example.backend.model.request.ReactRequest;
import com.example.backend.model.respon.ReactResponse;

public interface ReactService {
    // React hoặc hủy react trên bài viết/bình luận (toggle reaction)
    ReactResponse reactPostOrComment(Long userId, ReactRequest request);

    // Lấy danh sách react của một bài viết
    java.util.List<ReactResponse> getReactionsByPostId(Long postId);

    // Lấy danh sách react của một bình luận
    java.util.List<ReactResponse> getReactionsByCommentId(Long commentId);
}
