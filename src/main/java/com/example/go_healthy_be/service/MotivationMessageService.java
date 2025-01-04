package com.example.go_healthy_be.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.go_healthy_be.entity.Motivation;
import com.example.go_healthy_be.entity.Role;
import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.CreateMotivationRequest;
import com.example.go_healthy_be.model.MotivationMessageResponse;
import com.example.go_healthy_be.model.UpdateMessageRequest;
import com.example.go_healthy_be.repository.MotivationMessageRepository;

@Service
public class MotivationMessageService {

    @Autowired
    private MotivationMessageRepository motivationMessageRepository;
    
    @Autowired
    private ValidationService validationService;    


    public MotivationMessageResponse createMessage(User user, Role role, CreateMotivationRequest request){
        validationService.validate(request);
        if(user == null || role != Role.ADMIN){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to create message");
        }

        Motivation motivation = new Motivation();
        motivation.setMotivationId(UUID.randomUUID().toString());
        motivation.setMessage(request.getMessage());
        motivation.setCreatedAt(LocalDateTime.now()); 
        motivationMessageRepository.save(motivation);
        
        return toMotivationResponse(motivation);

        
    }

    private MotivationMessageResponse toMotivationResponse(Motivation motivation){
        return MotivationMessageResponse.builder()
                .motivationId(motivation.getMotivationId())
                .message(motivation.getMessage())
                .createdAt(motivation.getCreatedAt())
                .build();
    }
    public  MotivationMessageResponse getMotivationById(String motivationId){
        Motivation motivation = motivationMessageRepository.findById(motivationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Motivation not found"));
        return toMotivationResponse(motivation);
    }

    public MotivationMessageResponse deleteMessageById( User user, Role role, String motivationId){
        if(user == null || role != Role.ADMIN){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to delete content");
        }
        Motivation motivation = motivationMessageRepository.findById(motivationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Motivation not found"));
        motivationMessageRepository.delete(motivation);
        return toMotivationResponse(motivation);
    }

    public MotivationMessageResponse updateMessage(User user, Role role, String motivationId, UpdateMessageRequest request){
        validationService.validate(request);
        if(user == null || role != Role.ADMIN){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to update content");
        }
        Motivation motivation = motivationMessageRepository.findById(motivationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Motivation not found"));
        motivation.setMessage(request.getMessage());
        motivationMessageRepository.save(motivation);
        return toMotivationResponse(motivation);
    }

    public List<MotivationMessageResponse> getAllMessage(){
        List<Motivation> motivations = motivationMessageRepository.findAll();
        return motivations.stream()
                .map(motivation ->new MotivationMessageResponse(motivation.getMotivationId(), motivation.getMessage(), motivation.getCreatedAt()))
                .collect(Collectors.toList());
    }
}
