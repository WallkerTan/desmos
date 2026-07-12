package com.example.backend.model.respon;

import java.time.LocalDateTime;
import com.example.backend.model.enums.Reaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactResponse {
    private Long id;
    private Reaction reaction;
    private UserResponse user;
    private Long postId;
    private Long commentId;
    private LocalDateTime createAt;
}
