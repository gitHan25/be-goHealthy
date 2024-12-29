package com.example.go_healthy_be.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class UpdateFoodRequest {

    @JsonIgnore
    @NotBlank
    private String foodId;

    @NotBlank
    @Size(max = 128)
    private String foodName;

    @DecimalMin("0.0")
    @DecimalMax("1000.0")
    private Double calories;
   
    private LocalDateTime consumptionDate;

    @Min(0)
    @Max(1000)
    private int quantity;
}
