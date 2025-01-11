package com.eroskoller.security.controller;


import com.eroskoller.security.entity.CustomerEntity;
import com.eroskoller.security.entity.LoanEntity;
import com.eroskoller.security.repository.CustomerRepository;
import com.eroskoller.security.repository.LoanRepository;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
