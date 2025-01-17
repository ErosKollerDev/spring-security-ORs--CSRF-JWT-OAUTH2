package com.eroskoller.security.controller;


import com.eroskoller.security.entity.AccountTransactionEntity;
import com.eroskoller.security.entity.CustomerEntity;
import com.eroskoller.security.repository.AccountTransactionRepository;
import com.eroskoller.security.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class BalanceController {

    private final CustomerRepository customerRepository;
    private final AccountTransactionRepository accountTransactionRepository;

    @GetMapping("balance")
    public ResponseEntity<List<AccountTransactionEntity>> getBalanceDetails(@RequestParam("email") String email) {
//        Optional<CustomerEntity> byId = this.customerRepository.findById(id);
        CustomerEntity customerEntity = customerRepository.findByEmail(email).orElse(null);
        Integer id = customerEntity.getCustomerId();
        List<AccountTransactionEntity> byCustomer =  this.accountTransactionRepository.findByCustomerIdOrderByTransactionDt(id); //this.accountTransactionRepository.findByCustomerId(id);
        if (byCustomer != null) {
            return ResponseEntity.ok(byCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
