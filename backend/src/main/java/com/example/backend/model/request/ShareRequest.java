package com.example.backend.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareRequest {
    @NotBlank(message = "khong duoc bo trong")
    private String caption;

    @Min(0)
    @NotBlank(message = "post_id khong duoc bo trong")
    private Long postId;
}
