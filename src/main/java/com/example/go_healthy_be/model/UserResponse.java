package com.example.go_healthy_be.model;

import com.example.go_healthy_be.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserResponse {
    private String email;
    private String name;
    private String username;
    private Role role;
}
