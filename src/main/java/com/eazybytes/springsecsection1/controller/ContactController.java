package com.eazybytes.springsecsection1.controller;


import com.eazybytes.springsecsection1.entity.ContactMessageEntity;
import com.eazybytes.springsecsection1.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class ContactController {

private final ContactMessageRepository contactMessageRepository;

    @PostMapping("/contact")
    public ResponseEntity<ContactMessageEntity> saveContactInquiryDetails(@RequestBody ContactMessageEntity contactMessageEntity) {
        contactMessageEntity.setCreateDt(LocalDate.now());
        contactMessageEntity.setContactId( "SR"+ (int)(Math.random()*1000000000));
        ContactMessageEntity save = this.contactMessageRepository.save(contactMessageEntity);
        return ResponseEntity.ok(save);
    }

}
