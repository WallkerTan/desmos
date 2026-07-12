package com.example.backend.model.entity;

import java.security.Timestamp;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.example.backend.model.enums.Gender;
import com.example.backend.model.enums.Role;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String phone;
    private String password;

    private String avata;
    @Column(name = "cover_photo")
    private String coverPhoto;
    private String bio;
    private LocalDateTime dod;
    private Gender gender;

    private Role role;
    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "false_login")
    private Integer falseLogin = 0;

    @Column(name = "lock_until")
    private Timestamp lockUntil;

    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "delete_at")
    private LocalDateTime deleteAt;
}
