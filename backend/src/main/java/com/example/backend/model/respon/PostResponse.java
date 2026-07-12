package com.example.backend.model.respon;

import java.time.LocalDateTime;
import com.example.backend.model.enums.PostStatus;
import com.example.backend.model.enums.Privacy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String caption;
    private UserResponse user;
    private Privacy privacy;
    private PostStatus status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
