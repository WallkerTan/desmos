package com.example.backend.model.respon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HastagResponse {
    private Long id;
    private String type;
    private Long postId;
}
