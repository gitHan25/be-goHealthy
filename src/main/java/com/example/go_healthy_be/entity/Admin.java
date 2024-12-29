package com.example.go_healthy_be.entity;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import lombok.AllArgsConstructor;

import lombok.Getter;

import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor

@Entity 
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    
}
