import java.util.Collection;
import java.util.List;

import com.example.withcustomauthdemo.aspect.AuthAspect;
import com.example.withcustomauthdemo.auth.SecurityContextProvider;
import com.example.withcustomauthdemo.controller.SampleController;
import com.example.withcustomauthdemo.service.StringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // Automatically configures MockMvc with all controllers
public class SampleControllerAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SecurityContextProvider securityContextProvider;

    @InjectMocks
    private AuthAspect authAspect;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenUserRole_thenDeleteEndpointForbidden() throws Exception {
        // Mock an authentication with the "USER" role
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);

        // Return this authentication from SecurityContextProvider
        when(securityContextProvider.getAuthentication()).thenReturn(authentication);

        // Perform request and expect a Forbidden status due to insufficient role
        mockMvc.perform(delete("/api/private")) // Ensure this path matches your controller mapping
               .andExpect(status().isForbidden());
    }

    // Test configuration to register aspect, controller, and mock bean
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

        // Explicitly register the controller if it's not auto-detected
        @Bean
        public SampleController sampleController() {
            return new SampleController(mock(StringService.class));
        }
    }
}
