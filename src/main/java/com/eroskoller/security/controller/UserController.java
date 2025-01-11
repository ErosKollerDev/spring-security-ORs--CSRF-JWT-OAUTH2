package com.eroskoller.security.controller;

import com.eroskoller.security.dto.CustomerDTO;
import com.eroskoller.security.entity.CustomerEntity;
import com.eroskoller.security.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class UserController {


    private final CustomerService customerService;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
    private final Environment env;

//    @PostMapping("/register")
//    public ResponseEntity saveCustomer(@RequestBody CustomerDTO customerDTO) {
//        try {
//            customerDTO.setPwd(this.passwordEncoder.encode(customerDTO.getPwd()));
//            customerDTO.setCreateDt(LocalDateTime.now());
//            CustomerEntity saved = customerService.save(customerDTO);
//            if (saved != null && saved.getCustomerId() != null) {
////                saved.setPwd("<PASSWORD REMOVED FROM RESPONSE>");
//                return ResponseEntity.status(HttpStatus.CREATED).body(saved);
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to save customer");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }


    @GetMapping("/user")
    public ResponseEntity<CustomerDTO> getUserAfterLogin(Authentication authentication) {
        CustomerEntity byEmail = customerService.findByEmail(authentication.getName());
        CustomerDTO buildDTO = CustomerDTO.builder()
                .customerId(byEmail.getCustomerId())
                .id(byEmail.getCustomerId())
                .email(byEmail.getEmail())
                .pwd("<PASSWORD REMOVED FROM RESPONSE>")
//                .role(byEmail.getRole())
                .mobileNumber(byEmail.getMobileNumber())
                .name(byEmail.getName())
                .createDt(byEmail.getCreateDt())
                .authorities(byEmail.getAuthorities())
                .build();
        return ResponseEntity.ok(buildDTO);
    }


}
