package com.example.backend.model.request;

import com.example.backend.annotation.ValidEmail;
import com.example.backend.annotation.ValidPassword;
import com.example.backend.annotation.ValidPhone;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @NotBlank(message = "ten khong duoc bo trong")
    private String username;
    @ValidEmail
    private String email;
    @ValidPhone(allowNull = true)
    private String phone;
    @ValidPassword
    private String password;
}
