package com.example.backend.service;

import com.example.backend.model.request.CommentRequest;
import com.example.backend.model.respon.CommentResponse;

public interface CommentService {
    // thêm bình luận
    CommentResponse createComment(Long userId, CommentRequest request);

    // chỉnh sửa bình luận
    CommentResponse updateComment(Long userId, Long commentId, String content);

    // Xóa mềm bình luận (cho phép chủ bình luận hoặc chủ bài viết xóa)
    Boolean deleteComment(Long userId, Long commentId);

    // Lấy danh sách bình luận gốc của bài viết (có phân trang)
    org.springframework.data.domain.Page<CommentResponse> getCommentsByPostId(Long postId, int page, int size);

    // Lấy danh sách bình luận trả lời (reply) của một bình luận cha
    org.springframework.data.domain.Page<CommentResponse> getRepliesByCommentId(Long commentId, int page, int size);
}
