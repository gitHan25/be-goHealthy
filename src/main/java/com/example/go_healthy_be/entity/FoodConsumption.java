package com.example.go_healthy_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "food_consumption")
public class FoodConsumption {
@Id
    private String food_id;
    private String foodName;
    private Double calories;
    private Date consumptionDate;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;
}
