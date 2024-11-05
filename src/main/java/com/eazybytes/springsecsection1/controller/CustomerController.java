package com.eazybytes.springsecsection1.controller;

import com.eazybytes.springsecsection1.dto.CustomerDTO;
import com.eazybytes.springsecsection1.entity.CustomerEntity;
import com.eazybytes.springsecsection1.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class CustomerController {


    private CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerEntity>> getCustomers(){
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/customers/by-email")
    public ResponseEntity<CustomerEntity> getCustomersByEmail(@RequestParam("email") String emailPathVariable){
        return ResponseEntity.ok(customerService.findByEmail(emailPathVariable));
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerEntity> getCustomersById(@PathVariable("id") Long id){
        CustomerEntity byId = customerService.findById(id);
        byId.setPwd(null);
        return ResponseEntity.ok(byId);
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerEntity> saveCustomer(CustomerDTO customerDTO){
        CustomerEntity saved = customerService.save(customerDTO);
        return ResponseEntity.ok(saved);
    }


}
