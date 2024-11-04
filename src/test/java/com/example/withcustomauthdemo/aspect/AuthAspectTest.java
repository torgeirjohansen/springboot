package com.example.withcustomauthdemo.aspect;

import java.util.Collection;
import java.util.List;

import com.example.withcustomauthdemo.auth.Auth;
import com.example.withcustomauthdemo.auth.SecurityContextProvider;
import com.example.withcustomauthdemo.auth.UnauthorizedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.example.withcustomauthdemo.auth.Auth.ROLE_ADMIN;
import static com.example.withcustomauthdemo.auth.Auth.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class AuthAspectTest {

    @Mock
    private SecurityContextProvider securityContextProvider;

    @Mock
    private Authentication authentication;

    @Mock
    private Auth auth;

    @InjectMocks
    private AuthAspect authAspect;

    @BeforeTest
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticate_UserIsAuthenticatedAndAuthorized() {
        when(securityContextProvider.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        var granted = List.of(new SimpleGrantedAuthority(ROLE_USER));
        when(authentication.getAuthorities()).thenReturn((Collection) granted);
        when(auth.roles()).thenReturn(new String[]{ROLE_USER});

        assertDoesNotThrow(() -> authAspect.authenticate(auth));
    }

    @Test
    public void testAuthenticate_UserIsNotAuthenticated() {
        when(securityContextProvider.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> authAspect.authenticate(auth));
        assertEquals("User is not authenticated", exception.getMessage());
    }

    @Test
    public void testAuthenticate_UserIsNotAuthorized() {
        when(securityContextProvider.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getAuthorities()).thenReturn((Collection) List.of(new SimpleGrantedAuthority(ROLE_USER)));
        when(auth.roles()).thenReturn(new String[]{ROLE_ADMIN});

        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> authAspect.authenticate(auth));
        assertEquals("User is not authorized for this endpoint", exception.getMessage());
    }
}