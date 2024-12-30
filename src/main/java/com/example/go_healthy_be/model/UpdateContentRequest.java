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

public class UpdateContentRequest {

    @JsonIgnore
    @NotBlank
    private String contentId;

    @Size(max =128 )
    private String contentTitle;

    @Size(max = 1000)
    private String bodyContent;
}
