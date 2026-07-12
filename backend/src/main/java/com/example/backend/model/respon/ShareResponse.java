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
public class ShareResponse {
    private Long id;
    private String caption;
    private UserResponse user;
    private PostResponse post;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
