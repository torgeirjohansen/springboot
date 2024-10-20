package com.example.withcustomauthdemo.controller;

import java.util.Collection;
import java.util.List;

import com.example.withcustomauthdemo.aspect.AuthAspect;
import com.example.withcustomauthdemo.auth.SecurityContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SampleController.class)
@Import({AuthAspect.class, SecurityContextService.class})
public class SampleControllerAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityContextService securityContextService;

    @Test
    public void whenUserRole_thenPrivateEndpointForbidden() throws Exception {
        // Mock an authentication with the "USER" role
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        Collection<GrantedAuthority> granted = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        when(authentication.getAuthorities()).thenReturn((Collection) granted);

        when(securityContextService.getAuthentication()).thenReturn(authentication);

        mockMvc.perform(get("/api/private"))
               .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenAdminRole_thenPrivateEndpointAccessible() throws Exception {
        // Mock an authentication with the "ADMIN" role
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        Collection<GrantedAuthority> granted = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        when(authentication.getAuthorities()).thenReturn((Collection) granted);
        when(securityContextService.getAuthentication()).thenReturn(authentication);

        mockMvc.perform(get("/api/private"))
               .andExpect(status().isOk());
    }

    @Test
    public void whenAdminRole_thenPublicEndpointAccessible() throws Exception {
        // Mock an authentication with the "ADMIN" role
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getAuthorities()).thenReturn(
            (Collection) List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        when(securityContextService.getAuthentication()).thenReturn(authentication);

        mockMvc.perform(get("/api/public"))
               .andExpect(status().isOk());
    }

    @Test
    public void whenUserRole_thenPublicEndpointForbidden() throws Exception {
        // Mock an authentication with the "USER" role
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getAuthorities()).thenReturn(
            (Collection) List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        when(securityContextService.getAuthentication()).thenReturn(authentication);

        mockMvc.perform(get("/api/public"))
               .andExpect(status().isForbidden());
    }
}