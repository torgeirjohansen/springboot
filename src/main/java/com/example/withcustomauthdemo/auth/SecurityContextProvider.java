package com.example.withcustomauthdemo.auth;

import org.springframework.security.core.Authentication;

public interface SecurityContextProvider {
    Authentication getAuthentication();
}
