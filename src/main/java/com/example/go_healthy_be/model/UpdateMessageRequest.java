package com.example.go_healthy_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class UpdateMessageRequest {

    @JsonIgnore
    @NotBlank
    private String motivationId;

    @Size(max = 200)
    private String message;
    

}
