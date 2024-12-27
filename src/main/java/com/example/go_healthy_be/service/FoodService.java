package com.example.go_healthy_be.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.go_healthy_be.entity.FoodConsumption;
import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.CreateFoodConsumptionRequest;
import com.example.go_healthy_be.model.FoodConsumptionResponse;
import com.example.go_healthy_be.repository.FoodConsumptionRepository;

@Service


public class FoodService {

@Autowired
private FoodConsumptionRepository foodRepository;

@Autowired
private ValidationService validationService;

@Transactional
public  FoodConsumptionResponse create( User user,CreateFoodConsumptionRequest request) {
    validationService.validate(request);

    FoodConsumption foodConsumption = new FoodConsumption();
    foodConsumption.setFood_id(UUID.randomUUID().toString());
    foodConsumption.setFoodName(request.getFoodName());
    foodConsumption.setCalories(request.getCalories());
    foodConsumption.setConsumptionDate(request.getConsumptionDate());
    foodConsumption.setQuantity(request.getQuantity());
    foodConsumption.setUser(user);

    foodRepository.save(foodConsumption);

    return FoodConsumptionResponse.builder()
    .food_id(foodConsumption.getFood_id())
    .foodName(foodConsumption.getFoodName())
    .calories(foodConsumption.getCalories())
    .consumptionDate(foodConsumption.getConsumptionDate())
    .quantity(foodConsumption.getQuantity())
    .build();
}
}
