package com.example.go_healthy_be.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.go_healthy_be.entity.Schedule;
import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.CreateScheduleRequest;

import com.example.go_healthy_be.model.ScheduleResponse;
import com.example.go_healthy_be.model.UpdateScheduleRequest;
import com.example.go_healthy_be.repository.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;



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
        

            @Transactional(readOnly = true)
            public ScheduleResponse get(User user, String scheduleId){
                Schedule schedule = scheduleRepository.findFirstByUserAndScheduleId(user, scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));

                return toScheduleResponse(schedule);
            }

            @Transactional(readOnly = true)
            public List<ScheduleResponse> getAllScheduleByUserId(User user){
                List<Schedule> schedules = scheduleRepository.findAllByUser(user);
                return schedules.stream()
                .map(schedule -> ScheduleResponse.builder()
                .scheduleId(schedule.getScheduleId())
                .scheduleName(schedule.getScheduleName())
                .scheduleTime(schedule.getScheduleTime())
                .scheduleType(schedule.getScheduleType())
                .scheduleDescription(schedule.getScheduleDescription())
                .build())
                .collect(Collectors.toList());
            }
        
            @Transactional
            public ScheduleResponse updateSchedule(User user, UpdateScheduleRequest request){
                validationService.validate(request);
                Schedule schedule = scheduleRepository.findFirstByUserAndScheduleId(user, request.getScheduleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));

                schedule.setScheduleName(request.getScheduleName());
                schedule.setScheduleDescription(request.getScheduleDescription());
                schedule.setScheduleTime(request.getScheduleTime());
                schedule.setScheduleType(request.getScheduleType());

                scheduleRepository.save(schedule);

                return toScheduleResponse(schedule);
            }

            @Transactional
            public void deleteScheduleById(User user, String scheduleId){
                Schedule schedule = scheduleRepository.findFirstByUserAndScheduleId(user, scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));

                scheduleRepository.delete(schedule);
            }

           


            

     
}
