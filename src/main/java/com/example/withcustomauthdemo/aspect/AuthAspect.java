package com.example.withcustomauthdemo.aspect;
import java.nio.file.AccessDeniedException;
import java.rmi.AccessException;
import javax.swing.UnsupportedLookAndFeelException;

import com.example.withcustomauthdemo.auth.Authenticated;
import com.example.withcustomauthdemo.auth.UnauthorizedException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Aspect
@Component
public class AuthAspect {

    @Before("@annotation(authenticated)")
    public void authenticate(Authenticated authenticated) {
        // This is a placeholder for the actual authentication logic.
        // For example, you can check the security context, a JWT token, or any other custom logic.
        boolean isAuthenticated = checkAuthentication();

        if (!isAuthenticated) {
            throw new UnauthorizedException("User is not authenticated");
        }
    }

    private boolean checkAuthentication() {
        // Replace this with actual authentication logic.
        // For simplicity, we will assume the user is not authenticated.
        return false; // Change to 'true' if you want to simulate an authenticated user.
    }
}
