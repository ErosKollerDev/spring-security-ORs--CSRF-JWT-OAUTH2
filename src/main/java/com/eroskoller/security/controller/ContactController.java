package com.eroskoller.security.controller;


import com.eroskoller.security.entity.ContactMessageEntity;
import com.eroskoller.security.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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


    @PostMapping("/contact-all")
    @PreFilter("filterObject.contactName.toLowerCase() != 'test'")
    @PostFilter("filterObject.contactName.toLowerCase() != 'test'")
    public List<ContactMessageEntity> saveAllContactInquiryDetails(@RequestBody List<ContactMessageEntity> contacts) {
        List<ContactMessageEntity> list = contacts.stream().map(cm -> {
            cm.setCreateDt(LocalDate.now());
            cm.setContactId("SR" + (int) (Math.random() * 1000000000));
            return cm;
        }).toList();
        Iterable<ContactMessageEntity> contactMessageEntities = this.contactMessageRepository.saveAll(list);
        List<ContactMessageEntity> contactMessageEntityList =   new ArrayList<>();// StreamSupport.stream(contactMessageEntities.spliterator(), false).toList();
        contactMessageEntities.forEach(contactMessageEntityList::add);
//        contactMessageEntityList.getLast().setContactName("test");
//        ContactMessageEntity save = this.contactMessageRepository.save(list);%
        return contactMessageEntityList;
    }


    @GetMapping("/contact")
//    @PostFilter("filterObject.contactName != 'test'")
    public ResponseEntity<Iterable<ContactMessageEntity>> getContactInquiryDetails() {
        Iterable<ContactMessageEntity> all = this.contactMessageRepository.findAll();
        return ResponseEntity.ok(all);
    }

}
