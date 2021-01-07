package com.example.statista.config;

import com.example.statista.util.AuthProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.servlet.http.HttpServletResponse;

@Profile("dev")
@Configuration
public class SecurityBeansConfig {
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CsrfTokenRepository csrfRepo() {
        HttpSessionCsrfTokenRepository repo = new HttpSessionCsrfTokenRepository();
        repo.setParameterName("_csrf");
        repo.setHeaderName("X-CSRF-TOKEN");
        return repo;
    }

    @Bean
    AuthProvider authProvider(){
        return new AuthProvider();
    }

    @Bean
    SimpleUrlAuthenticationFailureHandler authenticationFailureHandler(){
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    AccessDeniedHandler accessDeniedHandler(){
        return (request, response, accessDeniedException) -> {
            response.getOutputStream().print("Access denied!");
            response.setStatus(403);
        };
    }

    @Bean
    RestAuthenticationSuccessHandler authenticationSuccessHandler(){
        return new RestAuthenticationSuccessHandler();
    }

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint(){
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

}
