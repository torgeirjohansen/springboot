package com.example.withcustomauthdemo.service;

import com.example.withcustomauthdemo.model.StoredString;
import com.example.withcustomauthdemo.model.StringRequest;

public interface StringServiceI {
    StoredString storeString(StringRequest request);

    StoredString getStringById(String id);

    void deleteStringById(String id);
}
