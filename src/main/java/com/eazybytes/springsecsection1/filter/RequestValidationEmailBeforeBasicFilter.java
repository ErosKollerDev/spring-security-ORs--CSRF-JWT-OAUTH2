package com.eazybytes.springsecsection1.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Deprecated
public class RequestValidationEmailBeforeBasicFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String email = request.getParameter("email");
        String basicAuthHeader = request.getHeader("Authorization");
        if (basicAuthHeader != null && basicAuthHeader.startsWith("Basic ")) {
            try{
                String base64Credentials = basicAuthHeader.substring("Basic ".length()).trim();
                String credentials = new String(java.util.Base64.getDecoder().decode(base64Credentials), java.nio.charset.StandardCharsets.UTF_8);
                int colonIndex = credentials.indexOf(":");
                if (colonIndex >= 0) {
                    email = credentials.substring(0, colonIndex);
                    if(email.toLowerCase().contains("test")) {
//                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad credentials,must not contain 'test' in : " + email);
                        return;
                    }
                }
            }catch (IllegalArgumentException exception) {
                throw new BadCredentialsException("Failed to decode basic authentication token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
