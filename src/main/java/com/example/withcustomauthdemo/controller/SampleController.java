package com.example.withcustomauthdemo.controller;

import javax.validation.Valid;

import com.example.withcustomauthdemo.auth.Authenticated;
import com.example.withcustomauthdemo.model.StoredString;
import com.example.withcustomauthdemo.model.StringRequest;
import com.example.withcustomauthdemo.service.StringService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SampleController {
    private final StringService stringService;

    public SampleController(StringService stringService) {
        this.stringService = stringService;
    }

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint.";
    }


    @GetMapping("/{id}")
    public StoredString getStringById(@Valid @PathVariable String id) {
        return stringService.getStringById(id);
    }

    @Authenticated(roles = {"ROLE_ADMIN"})
    @PostMapping
    public StoredString storeString(@Valid @RequestBody StringRequest request) {
        StoredString storedString = stringService.storeString(request);
        return storedString;
    }

    @Authenticated(roles = {"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public void deleteStringById(@PathVariable String id) {
        stringService.deleteStringById(id);
    }
}