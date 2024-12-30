package com.example.go_healthy_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.go_healthy_be.entity.Motivation;

@Repository
public interface MotivationMessageRepository extends JpaRepository<Motivation, String> {

}
