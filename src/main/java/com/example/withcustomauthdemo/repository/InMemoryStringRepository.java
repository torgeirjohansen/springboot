package com.example.withcustomauthdemo.repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.example.withcustomauthdemo.model.StoredString;

public class InMemoryStringRepository implements StringRepository {
    private final ConcurrentMap<String, StoredString> store = new ConcurrentHashMap<>();

    public StoredString save(StoredString storedString) {
        store.put(storedString.id(), storedString);
        return storedString;
    }

    public Optional<StoredString> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public void deleteById(String id) {
        store.remove(id);
    }
}
