package com.example.go_healthy_be.service;


import com.example.go_healthy_be.entity.User;

import com.example.go_healthy_be.model.RegisterUserRequest;
import com.example.go_healthy_be.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Validator validator ;
    @Transactional
    public void register(RegisterUserRequest request) {

       Set<ConstraintViolation<RegisterUserRequest>> constraintViolations = validator.validate(request);
       if(constraintViolations.size() != 0 ){
           throw new ConstraintViolationException(constraintViolations);
       }
       if(userRepository.existsById(request.getEmail())){
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
