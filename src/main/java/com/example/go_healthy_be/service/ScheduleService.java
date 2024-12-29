package com.example.go_healthy_be.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.go_healthy_be.entity.Schedule;
import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.CreateScheduleRequest;

import com.example.go_healthy_be.model.ScheduleResponse;
import com.example.go_healthy_be.repository.ScheduleRepository;

import jakarta.transaction.Transactional;


@Service

public class ScheduleService {
@Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ValidationService validationService;


    @Transactional
    public ScheduleResponse create(User user, CreateScheduleRequest request){
        validationService.validate(request);

        Schedule schedule = new Schedule();
        schedule.setScheduleId(UUID.randomUUID().toString());
        schedule.setScheduleName(request.getScheduleName());
        schedule.setScheduleDescription(request.getScheduleDescription());
        schedule.setScheduleTime(request.getScheduleTime());
        schedule.setScheduleType(request.getScheduleType());
        schedule.setUser(user);

        scheduleRepository.save(schedule);

        return toScheduleResponse(schedule);
                
            }

            private ScheduleResponse toScheduleResponse(Schedule schedule){
                return ScheduleResponse.builder()
                .scheduleId(schedule.getScheduleId())
                .scheduleName(schedule.getScheduleName())
                .scheduleTime(schedule.getScheduleTime())
                .scheduleType(schedule.getScheduleType())
                .scheduleDescription(schedule.getScheduleDescription())
                .build();
            }
        
        
            

     
}
