package com.example.go_healthy_be.service;


import com.example.go_healthy_be.entity.User;

import com.example.go_healthy_be.model.RegisterUserRequest;
import com.example.go_healthy_be.repository.UserRepository;
import com.example.go_healthy_be.security.BCrypt;

import jakarta.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;




@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationService validationService ;
    @Transactional
    public void register(RegisterUserRequest request) {
validationService.validate(request);
        
       if(userRepository.existsByEmail(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered");
       }
       User user = new User();
       user.setEmail(request.getEmail());
       user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
       user.setUsername(request.getUsername());
       user.setName(request.getName());
        userRepository.save(user);

    }

    
}
