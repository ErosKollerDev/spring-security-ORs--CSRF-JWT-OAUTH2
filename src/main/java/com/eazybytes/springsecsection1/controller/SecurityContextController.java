package com.eazybytes.springsecsection1.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("security")
@RequiredArgsConstructor
public class SecurityContextController {




    @GetMapping("name")
    public String getCurrentUser() {
//        return securityContextHol
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return name;
    }

    @GetMapping("username")
    public String currentUserName(Authentication authentication){
        return authentication.getName();
    }

}
