package com.eazybytes.springsecsection1.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {



    @GetMapping("/")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome do Spring Boot Security with Security this Time");
    }

}
