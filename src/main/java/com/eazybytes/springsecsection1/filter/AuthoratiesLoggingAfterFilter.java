package com.eazybytes.springsecsection1.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class AuthoratiesLoggingAfterFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                (Authentication) request.getAttribute("SPRING_SECURITY_CONTEXT");
        if (authentication != null) {
            log.info("User is authenticated: {}", authentication.getName()+
                    ", authorities: " + authentication.getAuthorities());
        } else {
            log.info("User is not authenticated");
        }
        filterChain.doFilter(request, response);
    }
}
