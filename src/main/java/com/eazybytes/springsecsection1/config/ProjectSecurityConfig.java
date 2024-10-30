package com.eazybytes.springsecsection1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> {
//                    requests.anyRequest()
//                            .permitAll();
//                            .denyAll();
//                        .authenticated()
                    requests.requestMatchers("/my/**")
                            .authenticated()
                            .requestMatchers("/contact", "/notices", "/error")
                            .permitAll();
                }
        );
        http.formLogin(withDefaults());
//        http.formLogin( f -> {
//            f.disable();
//        });
        http.httpBasic(withDefaults());
//        http.httpBasic(f -> f.disable());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User
                .withUsername("springsecurityadmin")
//                .password("{noop}admin")
//                .password("{ldap}admin")
//                .password("{MD4}admin")
//                .password("{MD5}admin")
//                .password("{SHA-1}admin")
//                .password("{sha256}admin")
//                .password("{argon2}admin")
                .password("{bcrypt}$2y$10$zWddi6AS4rGszcZB9KE8E.qOBT20Y1VzdT8wuf76Ox/zjCiCPyF/u")//zerospringsecurity
                .roles("ADMIN")
                .authorities("READ_PRIVILEGE", "WRITE_PRIVILEGE", "admin")
                .build();

        UserDetails user = User.withUsername("user")
//                .password("{noop}user")
                .password("{noop}user") //user
                .authorities("read")
                .roles("USER")
                .build();

//        UserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(admin);
//        manager.createUser(user);
//        return  manager;
        return new InMemoryUserDetailsManager(admin, user);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();// will be used by Spring Security BCryptPasswordEncoder by default
        return delegatingPasswordEncoder;
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
