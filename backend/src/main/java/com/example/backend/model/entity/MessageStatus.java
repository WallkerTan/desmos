package com.example.backend.model.entity;

import java.time.LocalDateTime;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@Table(name = "message_status")
@IdClass(MessageStatusId.class)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MessageStatus {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    private Message message;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_seen")
    private Boolean isSeen = false;

    @Column(name = "seen_at")
    private LocalDateTime seenAt;
}
