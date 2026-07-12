package com.example.backend.model.respon;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiDataResponse<T> {
    private boolean status;
    private String message;
    private T data;
    private HttpStatus httpStatus;
}
