package com.eazybytes.springsecsection1.exceptionhandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

import java.io.IOException;
import java.time.LocalDateTime;

public class BasicAuthenticationEntryPointCustom implements AuthenticationEntryPoint {

//    private String realmName;

//  1ic void afterPropertiesSet() {
//        Assert.hasText(this.realmName, "realmName must be specified");
//    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        // Populate dynamic values
        LocalDateTime currentTimeStamp = LocalDateTime.now();
        String message = (authException != null && authException.getMessage() != null) ? authException.getMessage()
                : "Unauthorized";
        String path = request.getRequestURI();
        response.setHeader("eazybank-error-reason", "Authentication failed");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("timestamp", currentTimeStamp);
        jsonResponse.put("status", HttpStatus.UNAUTHORIZED.value());
        jsonResponse.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        jsonResponse.put("message", message);
        jsonResponse.put("path", path);


        response.getWriter().write(jsonResponse.toString());
    }
}
