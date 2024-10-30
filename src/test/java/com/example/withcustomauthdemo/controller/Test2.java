import java.util.Collection;
import java.util.List;

import com.example.withcustomauthdemo.aspect.AuthAspect;
import com.example.withcustomauthdemo.auth.SecurityContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class Test2 {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private SecurityContextProvider securityContextProvider;

    @InjectMocks
    private AuthAspect authAspect;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Manually set up MockMvc with ApplicationContext and registered aspects
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext) // Use web context for aspect scanning
            .build();
    }

    @Test
    public void whenUserRole_thenPrivateEndpointForbidden() throws Exception {
        // Mock an authentication with the "USER" role
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);

        // Return this authentication from SecurityContextProvider
        when(securityContextProvider.getAuthentication()).thenReturn(authentication);

        // Perform request and expect a Forbidden status due to insufficient role
        mockMvc.perform(get("http://localhost:8080/api/123"))
               .andExpect(status().isForbidden());
    }

    // Test configuration to register aspect and mock bean
    @Configuration
    static class TestConfig {
        @Bean
        public AuthAspect authAspect(SecurityContextProvider securityContextProvider) {
            return new AuthAspect(securityContextProvider);
        }

        @Bean
        public SecurityContextProvider securityContextProvider() {
            return mock(SecurityContextProvider.class);
        }
    }
}
