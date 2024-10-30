package com.example.withcustomauthdemo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StringRequest(
    @NotBlank(message = "Content must not be blank")
    @Size(max = 255, message = "Content must not exceed 255 characters")
    String content) {}
