package com.eroskoller.security.controller;


import com.eroskoller.security.entity.AccountEntity;
import com.eroskoller.security.entity.CustomerEntity;
import com.eroskoller.security.repository.AccountRepository;
import com.eroskoller.security.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
