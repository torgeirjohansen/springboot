package com.example.withcustomauthdemo.service;

import java.util.UUID;

import com.example.withcustomauthdemo.model.StoredString;
import com.example.withcustomauthdemo.model.StringRequest;
import com.example.withcustomauthdemo.repository.StringRepository;

public class StringServiceImpl implements StringService {
    private final StringRepository repository;

    public StringServiceImpl(StringRepository repository) {
        this.repository = repository;
    }

    public StoredString storeString(StringRequest request) {
        String id = UUID.randomUUID().toString();
        StoredString storedString = new StoredString(id, request.content());
        return repository.save(storedString);
    }

    public StoredString getStringById(String id) {
        return repository.findById(id)
                         .orElseThrow(() -> new IllegalArgumentException("String with ID " + id + " not found"));
    }

    public void deleteStringById(String id) {
        repository.deleteById(id);
    }
}
