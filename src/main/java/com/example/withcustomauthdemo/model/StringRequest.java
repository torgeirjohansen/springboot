package com.example.withcustomauthdemo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StringRequest(
    @NotBlank(message = "Content must not be blank")
    @Size(max = 255, min = 1, message = "Content length must be between 1-255 characters")
    String content) {}
