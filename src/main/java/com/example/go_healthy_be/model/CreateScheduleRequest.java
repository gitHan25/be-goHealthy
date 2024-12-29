package com.example.go_healthy_be.model;

import java.time.LocalDateTime;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateScheduleRequest {
    
    @NotBlank
    private String scheduleName;

   @Size(max = 250)
    private String scheduleDescription;

    private LocalDateTime scheduleTime;
    
    @NotBlank
    private String scheduleType;
}
