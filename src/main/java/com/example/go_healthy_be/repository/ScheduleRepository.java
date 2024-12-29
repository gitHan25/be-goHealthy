package com.example.go_healthy_be.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.go_healthy_be.entity.Schedule;
import com.example.go_healthy_be.entity.User;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {

    Optional<Schedule> findFirstByUserAndScheduleId(User user, String scheduleId);

    List<Schedule> findAllByUser(User user);

    List<Schedule> findAllByScheduleTimeBetween(LocalDateTime start, LocalDateTime end);
}
