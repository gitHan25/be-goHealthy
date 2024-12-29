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

public class CreateContentRequest {

    @NotBlank
    @Size(max=128)
    private String contentTitle;

    @NotBlank
    @Size(max= 1000)
    private String bodyContent;
}
