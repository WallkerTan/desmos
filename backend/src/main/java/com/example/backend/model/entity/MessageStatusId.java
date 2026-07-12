package com.example.backend.model.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageStatusId implements Serializable {
    private Long message;
    private Long user;
}
