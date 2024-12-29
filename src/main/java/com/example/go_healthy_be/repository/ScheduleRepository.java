package com.example.go_healthy_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.go_healthy_be.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {

}
