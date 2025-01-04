package com.example.go_healthy_be.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "motivation_message")
public class Motivation {

    @Id
    @Column(name="id_motivation_message")
    private String motivationId;
    private String message;
    @Column(name="created_at")
    private LocalDateTime createdAt;

}
