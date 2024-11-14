package com.eazybytes.springsecsection1.config;

import com.eazybytes.springsecsection1.exceptionhandling.AccessDeniedHandlerCustom;
import com.eazybytes.springsecsection1.exceptionhandling.BasicAuthenticationEntryPointCustom;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class ProjectSecurityConfigCustomProd {


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {


        http.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.addAllowedOrigin("http://localhost:4200");
                config.addAllowedOrigin("http://localhost:4201");
                config.addAllowedOrigin("https://*");
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setMaxAge(3600L*24);
                return config;
            }
        }));
        http
//  Redirect to a custom page if the session is invalid
                .sessionManagement(sessionManagement ->
                        sessionManagement.invalidSessionUrl("/invalid-session")
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(true))
                .requiresChannel(requestChannleConfiguration ->
                        requestChannleConfiguration.anyRequest().requiresSecure()) // only HTTPS for production
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> {
//                    requests.anyRequest()
//                            .permitAll();
//                            .denyAll();
//                        .authenticated()
                            requests.requestMatchers("/my/**")
                                    .authenticated()
                                    .requestMatchers("/contact", "/notices", "/error", "/register", "/invalid-session")
                                    .permitAll();
                        }
                );
        http.formLogin(withDefaults());
//        http.formLogin( f -> {
//            f.disable();
//        });
//        http.httpBasic(withDefaults());
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new BasicAuthenticationEntryPointCustom()));
        //Global config
//        http.exceptionHandling( hbc-> hbc.authenticationEntryPoint(new BasicAuthenticationEntryPointCustom()));
//        http.httpBasic(f -> f.disable());

        http.exceptionHandling(hbc -> hbc.accessDeniedHandler(new AccessDeniedHandlerCustom())
//                .accessDeniedPage("/403-denied")
        );

        return http.build();
    }

    // We don't need this anymore cuz, we are using/implemeting UserDetailsService interface UserDetailsCustomService
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//        return jdbcUserDetailsManager;
//    }


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
