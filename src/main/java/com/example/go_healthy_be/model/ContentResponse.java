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

public class ContentResponse {

    private String contentId;
    private String contentTitle;
    private String bodyContent; 
    private LocalDateTime created_at;
}
