package com.example.backend.model.respon;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String content;
    private UserResponse user;
    private Long postId;
    private Long parentCommentId;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
