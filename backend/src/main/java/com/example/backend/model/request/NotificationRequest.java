package com.example.backend.model.request;

import com.example.backend.model.enums.NotificationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    @NotBlank(message = "phai co noi dung thong bao")
    private String message;
    @NotBlank(message = "nguyen nhan tb khong duoc bo trong")
    private NotificationType type;

    @NotNull(message = "khong duoc bo trong")
    private Boolean isRead;

    @NotBlank(message = "khong duoc bo trong")
    private String action;

    @Min(0)
    @NotBlank(message = "user_id khong duoc bo trong")
    private Long userId;

    @Min(0)
    @NotBlank(message = "taget_id khong duoc bo trong")
    private Long targetId;
}
