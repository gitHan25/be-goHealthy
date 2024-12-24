package com.example.go_healthy_be.model;

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


public class LoginUserRequest {
    @NotBlank
    @Size(max = 128)
    private String email;
    @Size(max = 128)
    private String password;

}
