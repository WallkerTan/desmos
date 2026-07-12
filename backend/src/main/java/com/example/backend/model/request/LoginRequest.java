package com.example.backend.model.request;

import com.example.backend.annotation.ValidEmail;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginRequest {
    @ValidEmail
    private String email;
    @NotBlank(message = "khong duoc bo trong mat khau")
    private String password;
}
