package com.example.backend.model.request;

import com.example.backend.model.enums.MediaType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostMediaRequest {

    @Min(0)
    @NotBlank(message = "post_id khong duoc bo trong")
    private Long postId;
    
    @NotBlank(message = "tep khong duoc bo trong")
    private String mediaUrl;

    @NotNull(message = "khong duoc bo trong")
    private MediaType mediaType;
}
