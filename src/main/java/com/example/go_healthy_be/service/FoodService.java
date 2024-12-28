package com.example.go_healthy_be.service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    foodConsumption.setFoodId(UUID.randomUUID().toString());
    foodConsumption.setFoodName(request.getFoodName());
    foodConsumption.setCalories(request.getCalories());
    foodConsumption.setConsumptionDate(request.getConsumptionDate());
    foodConsumption.setQuantity(request.getQuantity());
    foodConsumption.setUser(user);

    foodRepository.save(foodConsumption);

    return FoodConsumptionResponse.builder()
    .foodId(foodConsumption.getFoodId())
    .foodName(foodConsumption.getFoodName())
    .calories(foodConsumption.getCalories())
    .consumptionDate(foodConsumption.getConsumptionDate())
    .quantity(foodConsumption.getQuantity())
    .build();
    }

    @Transactional(readOnly = true)
    public FoodConsumptionResponse get(User user, String food_id) {
        FoodConsumption foodConsumption = foodRepository.findFirstByUserAndFoodId(user, food_id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found"));

        return FoodConsumptionResponse.builder()
        .foodId(foodConsumption.getFoodId())
        .foodName(foodConsumption.getFoodName())
        .calories(foodConsumption.getCalories())
        .consumptionDate(foodConsumption.getConsumptionDate())
        .quantity(foodConsumption.getQuantity())
        .build();
    }

    @Transactional(readOnly = true)
    public List<FoodConsumptionResponse> getAllByUser(User user) {
        List<FoodConsumption> foodConsumptions = foodRepository.findAllByUser(user);
        return foodConsumptions.stream()
                .map(foodConsumption -> FoodConsumptionResponse.builder()
                        .foodId(foodConsumption.getFoodId())
                        .foodName(foodConsumption.getFoodName())
                        .calories(foodConsumption.getCalories())
                        .consumptionDate(foodConsumption.getConsumptionDate())
                        .quantity(foodConsumption.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    }


