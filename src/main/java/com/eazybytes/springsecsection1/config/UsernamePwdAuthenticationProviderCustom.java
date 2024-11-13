package com.eazybytes.springsecsection1.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
//@Profile({"default", "dev", "local"})
@Profile({"default", "local"})
@RequiredArgsConstructor
@Slf4j
public class UsernamePwdAuthenticationProviderCustom implements AuthenticationProvider {

    private final UserDetailsServiceCustom userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("CustomUsernamePwdAuthenticationProvider.authenticate(), SPRING_PROFILES_ACTIVE : {}",System.getenv("SPRING_PROFILES_ACTIVE"));
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }


}
