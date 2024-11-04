package com.example.withcustomauthdemo.controller;

import java.util.Collection;
import java.util.List;

import com.example.withcustomauthdemo.auth.SecurityContextProvider;
import com.example.withcustomauthdemo.model.StoredString;
import com.example.withcustomauthdemo.model.StringRequest;
import com.example.withcustomauthdemo.service.StringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityContextProvider securityContextProvider;

    @MockBean
    private StringService stringService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void whenAdminRole_thenDeleteEndpointSuccess() throws Exception {
        setupSecurityContext("ROLE_ADMIN");

        mockMvc.perform(delete("/api/123"))
               .andExpect(status().isOk());
    }

    @Test
    public void whenAdminRole_thenDeleteEndpointSuccess2() throws Exception {
        setupSecurityContext("ROLE_ADMIN");

        mockMvc.perform(delete("/api/123"))
               .andExpect(status().isOk());
    }

    @Test
    public void whenUserRole_thenDeleteEndpointForbidden() throws Exception {
        setupSecurityContext("ROLE_USER");

        mockMvc.perform(delete("/api/123"))
               .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenUserRole_thenGetEndpointSuccess() throws Exception {
        setupSecurityContext("ROLE_USER");
        when(stringService.getStringById("123")).thenReturn(new StoredString("id", "Test String"));

        mockMvc.perform(get("/api/123"))
               .andExpect(status().isOk());
    }

    @Test
    public void whenInvalidRole_thenGetEndpointSuccess() throws Exception {
        setupSecurityContext("INVALID_ROLE");

        mockMvc.perform(get("/api/123"))
               .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenAdminRole_thenPostEndpointSuccess2() throws Exception {
        setupSecurityContext("ROLE_ADMIN");

        StringRequest request = new StringRequest("Test String");
        when(stringService.storeString(request)).thenReturn(new StoredString("id", "Test String"));

        mockMvc.perform(post("/api")
                            .contentType("application/json")
                            .content("{\"content\":\"\"}"))
               .andExpect(status().isOk());
    }

    private void setupSecurityContext(String role) {
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(securityContextProvider.getAuthentication()).thenReturn(authentication);
    }
}
