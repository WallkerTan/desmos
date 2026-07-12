package com.example.backend.model.respon;

import java.time.LocalDateTime;
import com.example.backend.model.enums.Gender;
import com.example.backend.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String avata;
    private String coverPhoto;
    private String bio;
    private LocalDateTime dod;
    private Gender gender;
    private Role role;
    private Boolean isVerified;
    private Boolean isActive;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
