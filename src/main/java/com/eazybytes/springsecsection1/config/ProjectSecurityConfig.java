package com.eazybytes.springsecsection1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import javax.sql.DataSource;

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
                    requests.requestMatchers("/my/**","/admin/**")
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
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        return jdbcUserDetailsManager;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();// will be used by Spring Security BCryptPasswordEncoder by default
        return delegatingPasswordEncoder;
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        HaveIBeenPwnedRestApiPasswordChecker haveIBeenPwnedRestApiPasswordChecker = new HaveIBeenPwnedRestApiPasswordChecker();
        return haveIBeenPwnedRestApiPasswordChecker;
    }
}
