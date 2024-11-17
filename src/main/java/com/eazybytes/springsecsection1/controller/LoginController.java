package com.eazybytes.springsecsection1.controller;

import com.eazybytes.springsecsection1.constants.ApplicationConstants;
import com.eazybytes.springsecsection1.dto.CustomerDTO;
import com.eazybytes.springsecsection1.dto.LoginRequestDTO;
import com.eazybytes.springsecsection1.dto.LoginResponseDTO;
import com.eazybytes.springsecsection1.entity.CustomerEntity;
import com.eazybytes.springsecsection1.service.CustomerService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {


    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Environment env;




    @PostMapping("/singin")
    public ResponseEntity<LoginResponseDTO> loginCustomer(@RequestBody LoginRequestDTO loginRequestDTO) {
        String jwt = "";
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequestDTO.username(), loginRequestDTO.password());
        Authentication authenticate = this.authenticationManager.authenticate(authentication);
        if (authenticate != null && authenticate.isAuthenticated()) {
            String secret = env.getProperty(ApplicationConstants.JWT_SECRET,
                    ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            jwt = Jwts.builder().issuer("Eazy Bank").subject("JWT Token")
                    .claim("username", authenticate.getName())
                    .claim("authorities", authenticate.getAuthorities().stream().map(
                            GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                    .issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime() + (1000 * 60 * 60 * 24)))//30000000 86400000  (1000 * 60 * 60 * 24)
                    .signWith(secretKey).compact();

            return ResponseEntity.status(HttpStatus.OK)
                    .header(ApplicationConstants.JWT_HEADER, jwt)
                    .body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(), jwt));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    @PostMapping("/singup")
    public ResponseEntity saveCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            customerDTO.setPwd(this.passwordEncoder.encode(customerDTO.getPwd()));
            customerDTO.setCreateDt(LocalDateTime.now());
            CustomerEntity saved = customerService.save(customerDTO);
            if (saved != null && saved.getCustomerId() != null) {
//                saved.setPwd("<PASSWORD REMOVED FROM RESPONSE>");
                return ResponseEntity.status(HttpStatus.CREATED).body(saved);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to save customer");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("/singin")
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
