package com.example.go_healthy_be.service;

import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.LoginUserRequest;
import com.example.go_healthy_be.model.TokenResponse;
import com.example.go_healthy_be.repository.UserRepository;
import com.example.go_healthy_be.security.BCrypt;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationService validationService;
    @Transactional
    public TokenResponse login(LoginUserRequest request){
        validationService.validate(request);

        User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Email or password wrong"));

        if(BCrypt.checkpw(request.getPassword(), user.getPassword())){
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(next30days());
            userRepository.save(user);
            return TokenResponse.builder()
            .token(user.getToken())
            .expiredAt(user.getTokenExpiredAt())
            .build();
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Email or password wrong");
        }
    }
    private Long next30days(){
        return System.currentTimeMillis() + (1000 * 16 *24 * 30 );
    }

}
