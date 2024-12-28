package com.example.go_healthy_be.repository;
import com.example.go_healthy_be.entity.FoodConsumption;
import com.example.go_healthy_be.entity.User;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface FoodConsumptionRepository extends  JpaRepository<FoodConsumption,String> {

        Optional<FoodConsumption> findFirstByUserAndFoodId(User user, String foodId);
}
