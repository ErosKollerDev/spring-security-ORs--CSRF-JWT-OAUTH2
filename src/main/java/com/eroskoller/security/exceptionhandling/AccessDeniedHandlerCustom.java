package com.eroskoller.security.exceptionhandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.time.LocalDateTime;

public class AccessDeniedHandlerCustom implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {


        // Populate dynamic values
        LocalDateTime currentTimeStamp = LocalDateTime.now();
        String message = (accessDeniedException != null && accessDeniedException.getMessage() != null) ? accessDeniedException.getMessage()
                : "Authorization Failed";
        String path = request.getRequestURI();
        response.setHeader("eazybank-denied-reason", "Authorization Failed");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("timestamp", currentTimeStamp);
        jsonResponse.put("status", HttpStatus.FORBIDDEN.value());
        jsonResponse.put("error", HttpStatus.FORBIDDEN.getReasonPhrase());
        jsonResponse.put("message", message);
        jsonResponse.put("path", path);


        response.getWriter().write(jsonResponse.toString());


    }
}
