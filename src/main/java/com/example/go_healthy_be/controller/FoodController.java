package com.example.go_healthy_be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.CreateFoodConsumptionRequest;
import com.example.go_healthy_be.model.FoodConsumptionResponse;
import com.example.go_healthy_be.model.UpdateFoodRequest;
import com.example.go_healthy_be.model.WebResponse;
import com.example.go_healthy_be.service.FoodService;

@CrossOrigin( origins = "*")
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
    path = "/api/food-consumption/{foodId}",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public WebResponse<FoodConsumptionResponse> get(User user, @PathVariable("foodId") String  foodId) {
    FoodConsumptionResponse foodConsumptionResponse = foodService.get(user, foodId);
    return WebResponse.<FoodConsumptionResponse>builder().data(foodConsumptionResponse).build();
    }


     @GetMapping(
        path = "/api/users/food-consumption",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public WebResponse<List<FoodConsumptionResponse>> getAllByUser(User user) {
    List<FoodConsumptionResponse> foodConsumptionResponses = foodService.getAllByUser(user);
    return WebResponse.<List<FoodConsumptionResponse>>builder().data(foodConsumptionResponses).build();
}


@PutMapping(
    path = "/api/food-consumption/{foodId}",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public WebResponse<FoodConsumptionResponse> updateFood(User user, @RequestBody UpdateFoodRequest request, @PathVariable("foodId") String foodId) {

    request.setFoodId(foodId);

    FoodConsumptionResponse foodConsumptionResponse = foodService.updateFood(user, request);
    return WebResponse.<FoodConsumptionResponse>builder().data(foodConsumptionResponse).build();
    }

    
    @DeleteMapping(
    path = "/api/food-consumption/{foodId}",
    produces = MediaType.APPLICATION_JSON_VALUE
)

public WebResponse<String> deleteById(User user, @PathVariable("foodId") String  foodId) {
    foodService.deleteById(user, foodId);
    return WebResponse.<String>builder().data("OK").build();
    }

}
