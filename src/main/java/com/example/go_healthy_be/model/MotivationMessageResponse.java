package com.example.go_healthy_be.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MotivationMessageResponse {

    private String motivationId;
    private String message;
    private LocalDateTime createdAt;

}
