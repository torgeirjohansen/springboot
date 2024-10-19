package com.example.withcustomauthdemo.controller;

import com.example.withcustomauthdemo.auth.Authenticated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SampleController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint.";
    }

    @Authenticated
    @GetMapping("/private")
    public String privateEndpoint() {
        return "This is a private endpoint, accessible only to authenticated users.";
    }
}