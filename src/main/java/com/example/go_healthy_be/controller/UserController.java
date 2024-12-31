package com.example.go_healthy_be.controller;



import com.example.go_healthy_be.entity.Role;
import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.RegisterUserRequest;
import com.example.go_healthy_be.model.UpdateUserRequest;
import com.example.go_healthy_be.model.UserResponse;
import com.example.go_healthy_be.model.WebResponse;
import com.example.go_healthy_be.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserController {


    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @PostMapping(path = "/api/users",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> register(@RequestBody RegisterUserRequest request) {
        userService.register(request);
        return WebResponse.<String>builder().data("OK").build();
    }
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping(path ="/api/users/current",produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UserResponse> get(User user){
        UserResponse userResponse = userService.get(user);
        
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    
    public WebResponse<List<UserResponse>> getAllUsers(Role role) {
       
        List<UserResponse> users = userService.getAllUsers(role);
        return WebResponse.<List<UserResponse>>builder().data(users).build();
    }
    
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @PatchMapping(
        path="/api/users/current",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> updateUser(User user, @RequestBody UpdateUserRequest request){
        UserResponse userResponse = userService.updateUser(user, request);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }
    }


