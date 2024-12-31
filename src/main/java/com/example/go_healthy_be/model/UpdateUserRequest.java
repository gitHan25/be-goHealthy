package com.example.go_healthy_be.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    
    @Size(max=100)
    private String username;
    @Size(max = 100)
    private String name;
    @Size(max = 128)
     private String password;

}
