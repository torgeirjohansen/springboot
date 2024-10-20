package com.example.withcustomauthdemo.repository;

import java.util.Optional;

import com.example.withcustomauthdemo.model.StoredString;

public interface StringRepository {
    StoredString save(StoredString storedString);

    Optional<StoredString> findById(String id);

    void deleteById(String id);
}
