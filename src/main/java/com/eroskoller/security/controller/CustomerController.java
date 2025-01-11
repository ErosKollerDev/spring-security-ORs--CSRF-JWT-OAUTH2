package com.eroskoller.security.controller;

import com.eroskoller.security.entity.CustomerEntity;
import com.eroskoller.security.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class CustomerController {


    private final CustomerService customerService;
//    private final PasswordEncoder passwordEncoder;

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerEntity>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/customers/by-email")
    public ResponseEntity<CustomerEntity> getCustomersByEmail(@RequestParam("email") String emailPathVariable) {
        return ResponseEntity.ok(customerService.findByEmail(emailPathVariable));
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerEntity> getCustomersById(@PathVariable("id") Long id) {
        CustomerEntity byId = customerService.findById(id);
        if (byId != null) {
            byId.setPwd("<PASSWORD REMOVED FROM RESPONSE>");
            return ResponseEntity.ok(byId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

//    @PostMapping("/customers")
//    public ResponseEntity saveCustomer(@RequestBody CustomerDTO customerDTO) {
//        try {
//            customerDTO.setPwd(this.passwordEncoder.encode(customerDTO.getPwd()));
//            CustomerEntity saved = customerService.save(customerDTO);
//            if (saved != null && saved.getCustomerId() != null) {
//                saved.setCustomerId(null);
//                return ResponseEntity.status(HttpStatus.CREATED).body(saved);
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to save customer");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//
//    }


}
