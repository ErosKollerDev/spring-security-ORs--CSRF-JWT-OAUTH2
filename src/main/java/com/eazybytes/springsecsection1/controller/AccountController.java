package com.eazybytes.springsecsection1.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my")
public class AccountController {



    @GetMapping("/account")
    public ResponseEntity<String> getAccountDetails() {
        return ResponseEntity.ok("My Account");
    }

}
