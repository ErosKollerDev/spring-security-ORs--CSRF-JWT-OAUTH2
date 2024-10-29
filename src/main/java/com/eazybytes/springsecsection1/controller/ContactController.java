package com.eazybytes.springsecsection1.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/welcome")
public class ContactController {



    @GetMapping("/contact")
    public ResponseEntity<String> saveContactInquiryDetails() {
        return ResponseEntity.ok("Contact Details Submitted Successfully");
    }

}
