package com.example.backend.model.respon;

import java.time.LocalDateTime;
import com.example.backend.model.enums.MediaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostMediaResponse {
    private Long id;
    private Long postId;
    private String mediaUrl;
    private MediaType mediaType;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
