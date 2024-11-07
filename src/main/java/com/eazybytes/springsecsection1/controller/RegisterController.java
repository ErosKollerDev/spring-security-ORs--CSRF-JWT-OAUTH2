package com.eazybytes.springsecsection1.controller;


import com.eazybytes.springsecsection1.dto.CustomerDTO;
import com.eazybytes.springsecsection1.entity.CustomerEntity;
import com.eazybytes.springsecsection1.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@AllArgsConstructor
@Slf4j
public class RegisterController {

    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("")
    public ResponseEntity saveCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            customerDTO.setPwd(this.passwordEncoder.encode(customerDTO.getPwd()));
            CustomerEntity saved = customerService.save(customerDTO);
            if (saved != null && saved.getId() != null) {
                saved.setId(null);
                return ResponseEntity.status(HttpStatus.CREATED).body(saved);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to save customer");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
