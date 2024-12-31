package com.example.go_healthy_be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.CreateScheduleRequest;
import com.example.go_healthy_be.model.ScheduleResponse;
import com.example.go_healthy_be.model.UpdateScheduleRequest;
import com.example.go_healthy_be.model.WebResponse;
import com.example.go_healthy_be.service.ScheduleService;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    
     @CrossOrigin(origins = "http://127.0.0.1:3000")
    @PostMapping(
        path = "/api/schedule",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    
    public WebResponse<ScheduleResponse> create(User user, @RequestBody CreateScheduleRequest request){
        ScheduleResponse scheduleResponse = scheduleService.create(user, request);
        return WebResponse.<ScheduleResponse>builder().data(scheduleResponse).build();
    }

    @GetMapping(
        path = "/api/schedule/{scheduleId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public WebResponse<ScheduleResponse> get(User user, @PathVariable("scheduleId") String scheduleId){
        ScheduleResponse scheduleResponse = scheduleService.get(user, scheduleId);
        return WebResponse.<ScheduleResponse>builder().data(scheduleResponse).build();
    }
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping(
        path = "/api/users/schedule",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ScheduleResponse>> getAllByUser(User user){
        List<ScheduleResponse> scheduleResponses = scheduleService.getAllScheduleByUserId(user);
        return WebResponse.<List<ScheduleResponse>>builder().data(scheduleResponses).build();
    }

    

    @PutMapping(
        path = "/api/schedule/{scheduleId}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ScheduleResponse> updateSchedule(User user, @PathVariable("scheduleId") String scheduleId, @RequestBody UpdateScheduleRequest request){
        request.setScheduleId(scheduleId);
        ScheduleResponse scheduleResponse = scheduleService.updateSchedule(user,request);
        return WebResponse.<ScheduleResponse>builder().data(scheduleResponse).build();
    }

    @DeleteMapping(
        path = "/api/schedule/{scheduleId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> deleteSchedule(User user, @PathVariable("scheduleId") String scheduleId){
        scheduleService.deleteScheduleById(user, scheduleId);
        return WebResponse.<String>builder().data("OK").build();
    }
}
