package com.eazybytes.springsecsection1.config;

import com.eazybytes.springsecsection1.entity.CustomerEntity;
import com.eazybytes.springsecsection1.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceCustom implements UserDetailsService {


    //    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    /**
     * Loads the user by their email address.
     *
     * @param email the email address of the user to be loaded.
     * @return UserDetails object containing user information.
     * @throws UsernameNotFoundException if the user with the specified email is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomerEntity customerEntity =
                this.customerRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new UsernameNotFoundException("User with email: " + email + " not found"));
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = List.of(new SimpleGrantedAuthority(customerEntity.getRole()));
        User user = new User(customerEntity.getEmail(),
                customerEntity.getPwd(),
                true,
                true,
                true,
                true, simpleGrantedAuthorities);
        return user;
    }
}
