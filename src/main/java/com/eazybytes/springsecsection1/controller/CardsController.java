package com.eazybytes.springsecsection1.controller;


import com.eazybytes.springsecsection1.entity.CardEntity;
import com.eazybytes.springsecsection1.entity.CustomerEntity;
import com.eazybytes.springsecsection1.repository.CardRepository;
import com.eazybytes.springsecsection1.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class CardsController {


    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("/cards")
    public ResponseEntity<List<CardEntity>> getCardsDetails(@RequestParam("email") String email) {
        CustomerEntity customerEntity = customerRepository.findByEmail(email).orElse(null);
        Integer id = customerEntity.getCustomerId();
        Optional<List<CardEntity>> byCustomerId = this.cardRepository.findByCustomerId(id);
        return byCustomerId.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
