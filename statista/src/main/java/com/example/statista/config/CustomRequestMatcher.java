package com.example.statista.config;

import org.jboss.logging.Logger;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class CustomRequestMatcher implements RequestMatcher {
    private static final Logger logger = Logger.getLogger(CustomRequestMatcher.class);
    private final AntPathRequestMatcher[] requestMatchers = {
            new AntPathRequestMatcher("/h2-console/**"),
            new AntPathRequestMatcher("/h2-console"),
    };
    @Override
    public boolean matches(HttpServletRequest request) {
        for (AntPathRequestMatcher rm : requestMatchers) {
            if (rm.matches(request)) {
                logger.info("Request matcher " + rm.toString() + " will not be CSRF protected.");
                return false;
            }
            logger.info("Request matcher " + rm.toString() + " will be CSRF protected.");
        }

        return true;
    }
}
