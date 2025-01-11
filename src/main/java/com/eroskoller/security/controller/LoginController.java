package com.eroskoller.security.controller;

import com.eroskoller.security.dto.CustomerDTO;
import com.eroskoller.security.entity.CustomerEntity;
import com.eroskoller.security.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Deprecated
@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {


    private final CustomerService customerService;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
    private final Environment env;




//    @PostMapping("/singin")
//    public ResponseEntity<LoginResponseDTO> loginCustomer(@RequestBody LoginRequestDTO loginRequestDTO) {
//        String jwt = "";
//        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequestDTO.username(), loginRequestDTO.password());
//        Authentication authenticate = this.authenticationManager.authenticate(authentication);
//        if (authenticate != null && authenticate.isAuthenticated()) {
//            String secret = env.getProperty(ApplicationConstants.JWT_SECRET,
//                    ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
//            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//            jwt = Jwts.builder().issuer("Eazy Bank").subject("JWT Token")
//                    .claim("username", authenticate.getName())
//                    .claim("authorities", authenticate.getAuthorities().stream().map(
//                            GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
//                    .issuedAt(new Date())
//                    .expiration(new Date((new Date()).getTime() + (1000 * 60 * 60 * 24)))//30000000 86400000  (1000 * 60 * 60 * 24)
//                    .signWith(secretKey).compact();
//
//            return ResponseEntity.status(HttpStatus.OK)
//                    .header(ApplicationConstants.JWT_HEADER, jwt)
//                    .body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(), jwt));
//        }else{
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//    }


//    @PostMapping("/singup")
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


//    @GetMapping("/singin")
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
