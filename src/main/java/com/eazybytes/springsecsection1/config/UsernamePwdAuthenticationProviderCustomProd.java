package com.eazybytes.springsecsection1.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Deprecated
//@Component
//@Profile("prod")
@RequiredArgsConstructor
public class UsernamePwdAuthenticationProviderCustomProd implements AuthenticationProvider {

    private final UserDetailsServiceCustom userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        if (this.passwordEncoder.matches(password, userDetails.getPassword())) {
            //Your can fetch additional information from database, like age, country of origin etc
            return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Invalid credentials for user: " + username);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
