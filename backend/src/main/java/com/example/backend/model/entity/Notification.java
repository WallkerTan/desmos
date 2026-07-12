package com.example.backend.model.entity;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import com.example.backend.model.enums.NotificationType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Table(name = "notifications")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message = "";
    
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name = "is_read")
    private Boolean isRead;

    private String action = "system";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "target_id")
    private Long targetId; // id của post/comment/... tùy type

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
