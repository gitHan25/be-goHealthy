package com.example.go_healthy_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.go_healthy_be.entity.Role;
import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.ContentResponse;
import com.example.go_healthy_be.model.CreateContentRequest;
import com.example.go_healthy_be.model.WebResponse;
import com.example.go_healthy_be.service.ContentService;

@RestController

public class ContentController {

        @Autowired
        private ContentService contentService;  


    @PostMapping(
        path = "/api/content",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
        
    )
    public WebResponse<ContentResponse> createContent(User user, Role role,@RequestBody CreateContentRequest request){
        ContentResponse contentResponse = contentService.createContent(user, role,request);
        return WebResponse.<ContentResponse>builder().data(contentResponse).build();
    }

    @GetMapping(
        path = "/api/content/{contentId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContentResponse> getContentById(Role role, @PathVariable("contentId") String contentId){
        ContentResponse contentResponse = contentService.getContentById(role,contentId);
        return WebResponse.<ContentResponse>builder().data(contentResponse).build();
    }

        
}
