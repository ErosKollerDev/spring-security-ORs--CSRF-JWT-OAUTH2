package com.eazybytes.springsecsection1.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invalid-session")
public class InvalidSessionController {



    @GetMapping()
    public ResponseEntity<String> getAccountDetails() {
        return ResponseEntity.ok("<h1>Invalid Session</h1>");
    }

}
