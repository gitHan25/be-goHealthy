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

import com.example.go_healthy_be.entity.Role;
import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.ContentResponse;
import com.example.go_healthy_be.model.CreateContentRequest;
import com.example.go_healthy_be.model.UpdateContentRequest;

import com.example.go_healthy_be.model.WebResponse;
import com.example.go_healthy_be.service.ContentService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    public WebResponse<ContentResponse> getContentById(@PathVariable("contentId") String contentId){
        ContentResponse contentResponse = contentService.getContentById(contentId);
        return WebResponse.<ContentResponse>builder().data(contentResponse).build();
    }

    @PutMapping(
        path = "/api/content/{contentId}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )

        public WebResponse<ContentResponse> updateContent(User user,Role role,@PathVariable("contentId") String contentId, @RequestBody UpdateContentRequest request){
            request.setContentId(contentId);
            ContentResponse contentResponse = contentService.updateContent(user,role,contentId, request);
            return WebResponse.<ContentResponse>builder().data(contentResponse).build();
        }

        @DeleteMapping(
            path = "/api/content/{contentId}",
            produces = MediaType.APPLICATION_JSON_VALUE
        )
        public WebResponse<String> deleteContentById(User user,Role role,@PathVariable("contentId") String contentId){
            contentService.deleteContentById(user,role,contentId);
            return WebResponse.<String>builder().data("Content with id "+contentId+" has been deleted").build();
        }
        

        @GetMapping(
            path = "/api/contents",
            produces = MediaType.APPLICATION_JSON_VALUE
        )
        public WebResponse<List<ContentResponse>> getAllContent(){
            List<ContentResponse> contentResponses = contentService.getAllContent();
            return WebResponse.<List<ContentResponse>>builder().data(contentResponses).build();
        }


    }
