package com.example.go_healthy_be.controller;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.go_healthy_be.model.LoginUserRequest;
import com.example.go_healthy_be.model.TokenResponse;
import com.example.go_healthy_be.model.WebResponse;
import com.example.go_healthy_be.service.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

@PostMapping(
    path ="/api/auth/login",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)


    public WebResponse<TokenResponse> login(@RequestBody LoginUserRequest request){
        TokenResponse tokenResponse = authService.login(request);
        return WebResponse.<TokenResponse>builder().data(tokenResponse).build();
    }
}