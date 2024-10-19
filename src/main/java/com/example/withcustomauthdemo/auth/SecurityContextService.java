package com.example.withcustomauthdemo.auth;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

//@Service
public class SecurityContextService {
    public Authentication getAuthentication() {
        System.out.println("SecurityContextService.getAuthentication");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication();
    }
}
