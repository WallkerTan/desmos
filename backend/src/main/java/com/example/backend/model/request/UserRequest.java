package com.example.backend.model.request;

import java.time.LocalDateTime;
import com.example.backend.annotation.ValidEmail;
import com.example.backend.annotation.ValidPassword;
import com.example.backend.annotation.ValidPhone;
import com.example.backend.model.enums.Gender;
import com.example.backend.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "ten khong duoc bo trong")
    private String username;
    @ValidEmail
    private String email;
    @ValidPhone
    private String phone;
    @ValidPassword
    private String password;
    @NotBlank(message = "khong duoc bo trong")
    private String avata;
    @NotBlank(message = "khong duoc bo trong")
    private String coverPhoto;
    @NotBlank(message = "khong duoc bo trong")
    private String bio;
    @NotBlank(message = "khong duoc bo trong")
    private LocalDateTime dod;
    @NotBlank(message = "khong duoc bo trong")
    private Gender gender;
    @NotBlank(message = "khong duoc bo trong")
    private Role role;
}
