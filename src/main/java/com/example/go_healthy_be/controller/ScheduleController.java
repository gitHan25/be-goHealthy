package com.example.go_healthy_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.CreateScheduleRequest;
import com.example.go_healthy_be.model.ScheduleResponse;
import com.example.go_healthy_be.model.WebResponse;
import com.example.go_healthy_be.service.ScheduleService;

@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    
    @PostMapping(
        path = "/api/schedule",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    
    public WebResponse<ScheduleResponse> create(User user, @RequestBody CreateScheduleRequest request){
        ScheduleResponse scheduleResponse = scheduleService.create(user, request);
        return WebResponse.<ScheduleResponse>builder().data(scheduleResponse).build();
    }

    
}
