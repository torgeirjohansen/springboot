package com.example.withcustomauthdemo.controller;

import com.example.withcustomauthdemo.auth.Auth;
import com.example.withcustomauthdemo.model.StoredString;
import com.example.withcustomauthdemo.model.StringRequest;
import com.example.withcustomauthdemo.service.StringService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
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

    public SampleController(@Qualifier("stringServiceImpl") StringService stringService) {
        this.stringService = stringService;
    }

    @GetMapping("/{id}")
    @Auth(roles = {"ROLE_USER"})
    public StoredString getStringById(@Valid @PathVariable String id) {
        return stringService.getStringById(id);
    }

    @Auth(roles = {"ROLE_ADMIN"})
    @PostMapping
    public StoredString storeString(@Valid @RequestBody StringRequest request) {
        StoredString storedString = stringService.storeString(request);
        return storedString;
    }

    @Auth(roles = {"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public void deleteStringById(@PathVariable String id) {
        stringService.deleteStringById(id);
    }
}