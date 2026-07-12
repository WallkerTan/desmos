package com.example.backend.model.request;

import com.example.backend.model.enums.FriendStatus;
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
public class FriendRequest {
    
    @Min(0)
    @NotBlank(message = "user_id khong duoc bo trong")
    private Long user2Id;

    @NotBlank(message = "trang thai khong duoc bo trong")
    private FriendStatus status;
}
