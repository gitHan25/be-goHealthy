package com.example.go_healthy_be.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RegisterUserRequest {
    @NotBlank
    @Email
    @Size(max = 128)
    private String email;
    @NotBlank
    @Size(max = 100)
    private String username;
    @NotBlank
    @Size(max = 128)
    private String password;
    @Size(max = 100)
    private String name;
}
