package com.eazybytes.springsecsection1.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my")
public class LoansController {



    @GetMapping("/loans")
    public ResponseEntity<String> getLoansDetails() {
        return ResponseEntity.ok("My Loans");
    }

}
