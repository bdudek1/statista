package com.example.statista.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler extends AccessDeniedHandlerImpl {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,

                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        if (accessDeniedException instanceof MissingCsrfTokenException
                || accessDeniedException instanceof InvalidCsrfTokenException) {

            if(request.getRequestURI().contains("h2-console")){
                response.sendRedirect("/h2-console");
            }
        }

        super.handle(request, response, accessDeniedException);



    }
}
