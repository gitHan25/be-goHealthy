package com.example.go_healthy_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.CreateFoodConsumptionRequest;
import com.example.go_healthy_be.model.FoodConsumptionResponse;
import com.example.go_healthy_be.model.WebResponse;
import com.example.go_healthy_be.service.FoodService;

@RestController
public class FoodController {

@Autowired
private FoodService foodService;


@PostMapping(
    path = "/api/food-consumption",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public WebResponse<FoodConsumptionResponse> create(User user, @RequestBody CreateFoodConsumptionRequest request) {
    FoodConsumptionResponse foodConsumptionResponse = foodService.create(user, request);
    return WebResponse.<FoodConsumptionResponse>builder().data(foodConsumptionResponse).build();
    }


@GetMapping(
    path = "/api/food-consumption/{food_id}",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public WebResponse<FoodConsumptionResponse> get(User user, @PathVariable("food_id") String food_id) {
    FoodConsumptionResponse foodConsumptionResponse = foodService.get(user, food_id);
    return WebResponse.<FoodConsumptionResponse>builder().data(foodConsumptionResponse).build();
    }
}
