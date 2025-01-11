package com.eroskoller.security.config;

import com.eroskoller.security.entity.AuthorityEntity;
import com.eroskoller.security.entity.CustomerEntity;
import com.eroskoller.security.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
@Deprecated
//@Service
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

        List<AuthorityEntity> authoritiesEntities = customerEntity.getAuthorities();

        List<SimpleGrantedAuthority> simpleGrantedAuthorities =
                authoritiesEntities.stream()
                        .map(authority ->
                            new SimpleGrantedAuthority(authority.getName())
                        )
                        .toList();

//        simpleGrantedAuthorities.removeAll(List.of(null));

//        List<SimpleGrantedAuthority> simpleGrantedAuthorities = List.of(new SimpleGrantedAuthority(customerEntity.getRole())
//                ,new SimpleGrantedAuthority(customerEntity.getRole()));
        User user = new User(customerEntity.getEmail(),
                customerEntity.getPwd(),
                true,
                true,
                true,
                true, simpleGrantedAuthorities);
        return user;
    }
}
