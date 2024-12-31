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
import com.example.go_healthy_be.model.CreateMotivationRequest;
import com.example.go_healthy_be.model.MotivationMessageResponse;
import com.example.go_healthy_be.model.UpdateMessageRequest;
import com.example.go_healthy_be.model.WebResponse;
import com.example.go_healthy_be.service.MotivationMessageService;

@RestController

public class MotivationController {


    @Autowired
    private MotivationMessageService motivationMessageService;

    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @PostMapping(
        path = "/api/motivation",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<MotivationMessageResponse> createMessage(User user, Role role, @RequestBody CreateMotivationRequest request){
        MotivationMessageResponse motivationMessageResponse = motivationMessageService.createMessage(user, role, request);
        return WebResponse.<MotivationMessageResponse>builder().data(motivationMessageResponse).build();
    }

 @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping(
        path = "/api/motivation/{motivationId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<MotivationMessageResponse> getMessageById(@PathVariable("motivationId") String motivationId){
        MotivationMessageResponse motivationMessageResponse = motivationMessageService.getMotivationById(motivationId);
        return WebResponse.<MotivationMessageResponse>builder().data(motivationMessageResponse).build();
    }
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @DeleteMapping(
        path = "/api/motivation/{motivationId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> deleteMessageById(User user, Role role, @PathVariable("motivationId") String motivationId){
        motivationMessageService.deleteMessageById(user, role, motivationId);
        return WebResponse.<String>builder().data("OK").build();
    }
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @PutMapping(
        path = "/api/motivation/{motivationId}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public WebResponse<MotivationMessageResponse> updateMessage(User user, Role role, @PathVariable("motivationId") String motivationId, @RequestBody UpdateMessageRequest request){
        request.setMotivationId(motivationId);
        MotivationMessageResponse motivationMessageResponse = motivationMessageService.updateMessage(user, role, motivationId, request);
        return WebResponse.<MotivationMessageResponse>builder().data(motivationMessageResponse).build();
    }
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping(
        path = "/api/motivations",
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public WebResponse<List<MotivationMessageResponse>> getAllMessage(){
        List<MotivationMessageResponse> motivationMessageResponses = motivationMessageService.getAllMessage();
        return WebResponse.<List<MotivationMessageResponse>>builder().data(motivationMessageResponses).build();
    }
}
