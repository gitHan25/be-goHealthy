package com.example.go_healthy_be.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

public class UpdateScheduleRequest {

        @JsonIgnore
        @NotBlank
        private String scheduleId;

        @Size(max =128 )
        private String scheduleName;
        @Size(max = 128)
        private String scheduleType;
        @Size(max = 250)
        private String scheduleDescription;
        
        private LocalDateTime scheduleTime;

}
