package com.example.backend.model.request;

import com.example.backend.model.enums.PostStatus;
import com.example.backend.model.enums.Privacy;
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
public class PostRequest {
    @NotBlank(message = "khong dduoc bo trong")
    private String caption;
    @NotNull(message = "khong duoc bo trong")
    private Privacy privacy;
    @NotBlank(message = "khong duoc bo trong")
    private PostStatus status;
}
