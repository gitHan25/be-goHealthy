package com.example.go_healthy_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "food_consumption")
public class FoodConsumption {
@Id
    private String foodId;
    private String foodName;
    private Double calories;
    private LocalDateTime consumptionDate;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private User user;
}
