package com.eazybytes.springsecsection1.controller;

import com.eazybytes.springsecsection1.dto.CustomerDTO;
import com.eazybytes.springsecsection1.entity.CustomerEntity;
import com.eazybytes.springsecsection1.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class UserController {


    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity saveCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            customerDTO.setPwd(this.passwordEncoder.encode(customerDTO.getPwd()));
            customerDTO.setCreateDt(LocalDateTime.now());
            CustomerEntity saved = customerService.save(customerDTO);
            if (saved != null && saved.getCustomerId() != null) {
//                saved.setPwd("<PASSWORD REMOVED FROM RESPONSE>");
                return ResponseEntity.status(HttpStatus.CREATED).body(saved);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to save customer");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("/user")
    public ResponseEntity<CustomerEntity> getUserAfterLogin(Authentication authentication) {
        return ResponseEntity.ok(customerService.findByEmail(authentication.getName()));
    }


}
