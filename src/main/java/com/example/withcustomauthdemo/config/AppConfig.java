package com.example.withcustomauthdemo.config;

import java.util.List;
import java.util.function.Supplier;

import com.example.withcustomauthdemo.auth.SecurityContextService;
import com.example.withcustomauthdemo.repository.InMemoryStringRepository;
import com.example.withcustomauthdemo.repository.StringRepository;
import com.example.withcustomauthdemo.service.StringService;
import com.example.withcustomauthdemo.service.StringServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.withcustomauthdemo.auth.Auth.ROLE_USER;

@Configuration
public class AppConfig {

    @Bean
    public Supplier<List<String>> userRolesProvider() {
        return () -> List.of(ROLE_USER);
    }

    @Bean
    public SecurityContextService securityContextService() {
        return new SecurityContextService();
    }

    @Bean
    public StringService stringServiceImpl() {
        return new StringServiceImpl(stringRepository());
    }

    @Bean
    public StringRepository stringRepository() {
        return new InMemoryStringRepository();
    }
}
