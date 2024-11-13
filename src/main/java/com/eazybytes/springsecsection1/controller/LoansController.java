package com.eazybytes.springsecsection1.controller;


import com.eazybytes.springsecsection1.entity.CustomerEntity;
import com.eazybytes.springsecsection1.entity.LoanEntity;
import com.eazybytes.springsecsection1.repository.CustomerRepository;
import com.eazybytes.springsecsection1.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class LoansController {

private final LoanRepository loanRepository;
private final CustomerRepository customerRepository;

    @GetMapping("/loans")
    public ResponseEntity<List<LoanEntity>> getLoansDetails(Authentication authentication) {
        Optional<CustomerEntity> byEmail = this.customerRepository.findByEmail(authentication.getName());
        Optional<List<LoanEntity>> byCustomer = this.loanRepository.findByCustomer(byEmail.get());
        return ResponseEntity.ok(byCustomer.get());
    }

}
