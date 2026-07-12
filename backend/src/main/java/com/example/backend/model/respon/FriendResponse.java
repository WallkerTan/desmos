package com.example.backend.model.respon;

import java.time.LocalDateTime;
import com.example.backend.model.enums.FriendStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendResponse {
    private Long id;
    private UserResponse user1;
    private UserResponse user2;
    private FriendStatus status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
