package com.example.backend.service;

import com.example.backend.model.entity.User;
import com.example.backend.model.request.Account;
import com.example.backend.model.request.LoginRequest;

public interface AuthService {
    String login(LoginRequest request);
    User register(Account account);
    Boolean logout();
    Boolean forgotPassword(String email);
    Boolean resetPassword(String email, String newPassword);
    
}
