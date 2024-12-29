package com.example.go_healthy_be.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import com.example.go_healthy_be.entity.Content;
import com.example.go_healthy_be.entity.Role;
import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.ContentResponse;
import com.example.go_healthy_be.model.CreateContentRequest;
import com.example.go_healthy_be.repository.ContentRepository;



@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ValidationService validationService;    
    @Transactional
    public ContentResponse createContent(User user, Role role, CreateContentRequest request) {
        validationService.validate(request);

    
        if (user == null || role != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to create content");
        }

        Content content = new Content();
        content.setContentId(UUID.randomUUID().toString());
        content.setTitle(request.getContentTitle());
        content.setBodyContent(request.getBodyContent());
        content.setCreated_at(LocalDateTime.now());
        contentRepository.save(content);

        return toContentResponse(content);
    }

    private ContentResponse toContentResponse(Content content) {
        return ContentResponse.builder()
                .contentId(content.getContentId())
                .contentTitle(content.getTitle())
                .bodyContent(content.getBodyContent())
                .created_at(content.getCreated_at())
                .build();
    }

    @Transactional(readOnly = true)
    public ContentResponse getContentById(Role role,String contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found"));

        return toContentResponse(content);
    }    
}
