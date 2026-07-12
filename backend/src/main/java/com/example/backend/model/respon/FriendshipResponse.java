package com.example.backend.model.respon;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipResponse {
    private Long id;
    private UserResponse sender;
    private UserResponse receiver;
    private LocalDateTime createAt;
}
