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
public class FriendshipRequest {
    
    @Min(0)
    @NotBlank(message = "reveiver_id khong duoc bo trong")
    private Long receiverId;
}
