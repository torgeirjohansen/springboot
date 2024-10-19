package com.example.withcustomauthdemo.aspect;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.example.withcustomauthdemo.auth.Authenticated;
import com.example.withcustomauthdemo.auth.SecurityContextProvider;
import com.example.withcustomauthdemo.auth.UnauthorizedException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthAspect {

    private final SecurityContextProvider securityContextService;

    public AuthAspect(SecurityContextProvider securityContextService) {
        this.securityContextService = securityContextService;
    }

    @Before("@annotation(authenticated)")
    public void authenticate(Authenticated authenticated) {
        // Get the current authenticated user's roles
        Authentication authentication = securityContextService.getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not authenticated");
        }

        List<String> userRoles = authentication.getAuthorities().stream()
                                               .map(grantedAuthority -> grantedAuthority.getAuthority())
                                               .collect(Collectors.toList());

        // Get the allowed roles from the annotation
        String[] allowedRoles = authenticated.roles();

        // Check if the user has any of the required roles
        boolean isAuthorized = Arrays.stream(allowedRoles).anyMatch(userRoles::contains);
        if (!isAuthorized) {
            throw new UnauthorizedException("User is not authorized for this endpoint");
        }
    }
}
