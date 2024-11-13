package com.eazybytes.springsecsection1.config;

import com.eazybytes.springsecsection1.exceptionhandling.AccessDeniedHandlerCustom;
import com.eazybytes.springsecsection1.exceptionhandling.BasicAuthenticationEntryPointCustom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("!prod")
public class ProjectSecurityConfigCustom {


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http

//                Redirect to a custom page if the session is invalid
                .sessionManagement(sessionManagement ->
                        sessionManagement.invalidSessionUrl("/invalid-session")
                                .maximumSessions(3)
                                .maxSessionsPreventsLogin(true))

//                .requiresChannel(requestChannleConfiguration ->
//                        requestChannleConfiguration.anyRequest().requiresInsecure())// only HTTP
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> {
//                    requests.anyRequest()
//                            .permitAll();
//                            .denyAll();
//                        .authenticated()
                            requests.requestMatchers("/my/**", "/admin/**")
                                    .authenticated()
                                    .requestMatchers("/contact", "/notices", "/error", "/register", "/invalid-session")
                                    .permitAll();
                        }
                );

        /*
          Preventing session fixation strategy
         */
        //Default behavior, just changing que session ID, and not de details  Servlet 3.1
        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionFixation(sessionFixation -> {}
//                        sessionFixation.changeSessionId()
        ));
        //Create a new session
        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionFixation(sessionFixation -> {}
//                        sessionFixation.newSession()
                ));

        //Create a new session and copies all the exists session attributes to the new session (default for Servlet 3.0)
        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionFixation(sessionFixation -> {}
//                        sessionFixation.migrateSession()
        ));

        http.formLogin(withDefaults());
//        http.formLogin( f -> {
//            f.disable();
//        });
//        http.httpBasic(withDefaults());
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new BasicAuthenticationEntryPointCustom()));
        //Global config
//        http.exceptionHandling( hbc-> hbc.authenticationEntryPoint(new BasicAuthenticationEntryPointCustom()));
//        http.httpBasic(f -> f.disable());

        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new AccessDeniedHandlerCustom())
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
