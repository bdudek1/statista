/*
Freeware License, some rights reserved

Copyright (c) 2019 Iuliana Cosmina

Permission is hereby granted, free of charge, to anyone obtaining a copy 
of this software and associated documentation files (the "Software"), 
to work with the Software within the limits of freeware distribution and fair use. 
This includes the rights to use, copy, and modify the Software for personal use. 
Users are also allowed and encouraged to submit corrections and modifications 
to the Software for the benefit of other users.

It is not allowed to reuse,  modify, or redistribute the Software for 
commercial use in any way, or for a user's educational materials such as books 
or blog articles without prior permission from the copyright holder. 

The above copyright notice and this permission notice need to be included 
in all copies or substantial portions of the software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS OR APRESS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.example.statista.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationProvider authProvider = new AuthenticationProvider() {
        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            return new UsernamePasswordAuthenticationToken(authentication.getName(),
                                                           authentication.getCredentials().toString(),
                                                           authentication.getAuthorities());
        }

        @Override
        public boolean supports(Class<?> authentication) {
            return authentication.equals(UsernamePasswordAuthenticationToken.class);
        }
    };

    AccessDeniedHandler accessDeniedHandler = (request, response, accessDeniedException) -> {
        response.getOutputStream().print("Access denied!");
        response.setStatus(403);
    };

    AuthenticationEntryPoint restAuthenticationEntryPoint = (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    SimpleUrlAuthenticationFailureHandler authenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Autowired
    RestAuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                                .password(passwordEncoder().encode("user"))
                                .roles("USER")
                                .build();

        UserDetails admin = User.withUsername("admin")
                                .password(passwordEncoder()
                                .encode("admin"))
                                .roles("ADMIN")
                                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                //.authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .mvcMatchers("/home/**").hasAnyRole("ADMIN", "USER")
                .mvcMatchers("/input/**").hasAnyRole("ADMIN", "USER")
                .mvcMatchers("/**").hasAnyRole("ADMIN", "USER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .defaultSuccessUrl("/home")
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and()
                .csrf().csrfTokenRepository(csrfRepo())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .maximumSessions(1)
                .expiredUrl("/sessionExpired.html")
                .and()
                .sessionFixation().migrateSession();
    }

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
}
