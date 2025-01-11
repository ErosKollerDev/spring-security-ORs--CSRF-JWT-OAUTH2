package com.eazybytes.springsecsection1.controller;


import com.eazybytes.springsecsection1.entity.AccountEntity;
import com.eazybytes.springsecsection1.entity.CustomerEntity;
import com.eazybytes.springsecsection1.repository.AccountRepository;
import com.eazybytes.springsecsection1.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("/account")
    public ResponseEntity<List<AccountEntity>> getAccountDetails(@RequestParam("email") String email) {
        CustomerEntity customerEntity = customerRepository.findByEmail(email).orElse(null);
        if(customerEntity != null){
            List<AccountEntity> byCustomer = accountRepository.findByCustomer(customerEntity);
            return ResponseEntity.ok(byCustomer);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
