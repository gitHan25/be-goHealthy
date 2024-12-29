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

public class ScheduleResponse {

    private String scheduleId;
    private String scheduleName;
    private String scheduleDescription;
    private LocalDateTime scheduleTime;
    private String scheduleType;
   

}
