package com.eazybytes.springsecsection1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;

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

}
