package com.example.go_healthy_be.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.go_healthy_be.entity.FoodConsumption;
import com.example.go_healthy_be.entity.User;
@Repository
public interface FoodConsumptionRepository extends  JpaRepository<FoodConsumption,String> {

        Optional<FoodConsumption> findFirstByUserAndFoodId(User user, String foodId);

        
        List<FoodConsumption> findAllByUser(User user);
}
