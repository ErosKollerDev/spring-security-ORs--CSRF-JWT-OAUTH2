package com.eazybytes.springsecsection1.controller;


import com.eazybytes.springsecsection1.entity.CustomerEntity;
import com.eazybytes.springsecsection1.entity.LoanEntity;
import com.eazybytes.springsecsection1.repository.CustomerRepository;
import com.eazybytes.springsecsection1.repository.LoanRepository;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
//    @PostAuthorize("hasRole('USER')")
    public ResponseEntity<List<LoanEntity>> getLoansDetails( @PathParam("email")String email) {
        Optional<CustomerEntity> byEmail = this.customerRepository.findByEmail(email);
        if(byEmail.isPresent()){
            Optional<CustomerEntity> byId = this.customerRepository.findById(byEmail.get().getCustomerId());
            Optional<List<LoanEntity>> byCustomer = this.loanRepository.findByCustomer(byId.get());
            return ResponseEntity.ok(byCustomer.get());
        }else{
            return null;
        }

    }

}
