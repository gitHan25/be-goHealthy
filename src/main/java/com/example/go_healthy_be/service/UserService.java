package com.example.go_healthy_be.service;


import com.example.go_healthy_be.entity.Role;
import com.example.go_healthy_be.entity.User;

import com.example.go_healthy_be.model.RegisterUserRequest;
import com.example.go_healthy_be.model.UpdateUserRequest;
import com.example.go_healthy_be.model.UserResponse;
import com.example.go_healthy_be.repository.UserRepository;
import com.example.go_healthy_be.security.BCrypt;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
       user.setRole(Role.USER);
       
        userRepository.save(user);

    }

    public UserResponse get(User user){
        return UserResponse.builder()
        .name(user.getName())
        .email(user.getEmail())
        .username(user.getUsername())
        .build();
    }
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
            .map(user -> new UserResponse(user.getEmail(), user.getUsername(),user.getName()))
            .collect(Collectors.toList());
    }
    @Transactional
    public UserResponse updateUser(User user,UpdateUserRequest request){
        validationService.validate(request);
        if(Objects.nonNull(request.getName())){
            user.setName(request.getName());
        }
        if(Objects.nonNull(request.getPassword())){
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }
        if(Objects.nonNull(request.getUsername())){
            user.setUsername(request.getUsername());
        }
        userRepository.save(user);

        return UserResponse.builder()
        .name(user.getName())
        .username(user.getUsername())
        .build();
    }


   
    
}
