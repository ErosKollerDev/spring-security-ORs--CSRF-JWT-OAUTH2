package com.eazybytes.springsecsection1.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my")
public class BalanceController {



    @GetMapping("balance")
    public ResponseEntity<String> getBalanceDetails() {
        return ResponseEntity.ok("My Balance");
    }

}
