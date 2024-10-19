package com.example.withcustomauthdemo.config;

import java.util.List;
import java.util.function.Supplier;

import com.example.withcustomauthdemo.auth.SecurityContextService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Supplier<List<String>> userRolesProvider() {
        return () -> List.of("USER");
    }

    @Bean
    public SecurityContextService securityContextService() {
        return new SecurityContextService();
    }

}
