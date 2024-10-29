package com.eazybytes.springsecsection1.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("")
public class NoticesController {



    @GetMapping("/notices")
    public ResponseEntity<String> getNotices() {
        return ResponseEntity.ok("My Notices");
    }

}
