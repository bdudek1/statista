package com.example.statista.config;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class CustomRequestMatcher implements RequestMatcher {
    private AntPathRequestMatcher[] requestMatchers = {
            new AntPathRequestMatcher("/h2-console/**"),
            new AntPathRequestMatcher("/h2-console"),
    };
    @Override
    public boolean matches(HttpServletRequest request) {
        for (AntPathRequestMatcher rm : requestMatchers) {
            if (rm.matches(request)) { return false; }
        }

        return true;
    }
}
