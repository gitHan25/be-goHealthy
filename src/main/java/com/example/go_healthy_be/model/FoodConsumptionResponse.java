package com.example.go_healthy_be.model;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodConsumptionResponse {

    private String food_id;
    private String foodName;
    private Double calories;
    private Date consumptionDate;
    private int quantity;
    
}