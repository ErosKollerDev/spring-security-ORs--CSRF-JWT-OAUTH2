package com.eroskoller.security.config;

import com.eroskoller.security.exceptionhandling.AccessDeniedHandlerCustom;
import com.eroskoller.security.filter.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@Profile("prod")
public class ProjectSecurityConfigCustomProd {


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakRoleConverter());

        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        csrfTokenRequestAttributeHandler.setCsrfRequestAttributeName("XSRF-TOKEN");

        http

//                Redirect to a custom page if the session is invalid
//                .sessionManagement(sessionConfig ->
//                        sessionConfig.invalidSessionUrl("/invalid-session")
//                                .maximumSessions(3)
//                                .maxSessionsPreventsLogin(true))
                /* not required when working with stateless session jwt token */
//                .securityContext(securityContextConfiguration -> securityContextConfiguration.requireExplicitSave(false))
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .maximumSessions(3)
                        .maxSessionsPreventsLogin(true))
                .requiresChannel(requestChannelConfiguration ->
                        requestChannelConfiguration.anyRequest().requiresSecure())// only HTTPS
//                .csrf(csrf -> csrf.disable())
                .csrf(csrfConfig -> csrfConfig
                        .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/contact", "/register","/login/**"))
//                .addFilterBefore(new RequestValidationEmailBeforeBasicFilter(), BasicAuthenticationFilter.class)
//                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
//                .addFilterAt(new AuthoratiesLoggingAtFilter(), BasicAuthenticationFilter.class)
//                .addFilterAfter(new AuthoratiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
//                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
//                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.addAllowedOrigin("http://localhost:4200");
                        config.addAllowedOrigin("http://localhost:4201");
                        config.addAllowedOrigin("http://*");
                        config.addAllowedOrigin("https://*");
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L / 4);
                        return config;
                    }
                }))

//                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> {
//                    requests.anyRequest()
//                            .permitAll();
//                            .denyAll();
//                        .authenticated()
                            requests
                                    .requestMatchers("/admin/**").authenticated()
//                                    .requestMatchers("/my/**").authenticated()
                                    .requestMatchers("/my/account").hasAnyAuthority("VIEWACCOUNT", "MASTER_OF_THE_UNIVERSE", "ADMIN")
                                    .requestMatchers("/my/account").hasAnyRole("ADMIN")
                                    .requestMatchers("/my/cards").hasAnyAuthority("VIEWCARDS", "MASTER_OF_THE_UNIVERSE", "ADMIN")
                                    .requestMatchers("/my/cards").hasAnyRole("ADMIN")
//                                    .requestMatchers("/my/loans").hasAnyAuthority("VIEWLOANS", "MASTER_OF_THE_UNIVERSE", "ADMIN")
//                                    .requestMatchers("/my/loans").hasAnyRole("ADMIN")
                                    .requestMatchers("/my/loans").authenticated()
                                    .requestMatchers("/my/balance").hasAnyAuthority("VIEWBALANCE", "VIEWACCOUNT", "MASTER_OF_THE_UNIVERSE", "ADMIN")
                                    .requestMatchers("/my/balance").hasAnyRole("ADMIN")
                                    .requestMatchers("/contact", "/notices", "/error", "/register", "/invalid-session","/login/**").permitAll();
                        }
                );

        /*
          Preventing session fixation strategy
         */
        //Default behavior, just changing que session ID, and not de details  Servlet 3.1
        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionFixation(sessionFixation -> {
                        }
//                        sessionFixation.changeSessionId()
                ));
        //Create a new session
        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionFixation(sessionFixation -> {
                        }
//                        sessionFixation.newSession()
                ));

        //Create a new session and copies all the exists session attributes to the new session (default for Servlet 3.0)
        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionFixation(sessionFixation -> {
                        }
//                        sessionFixation.migrateSession()
                ));

//        http.formLogin(withDefaults());
//        http.formLogin( f -> {
//            f.disable();
//        });
//        http.httpBasic(withDefaults());
//        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new BasicAuthenticationEntryPointCustom()));
        //Global config
//        http.exceptionHandling( hbc-> hbc.authenticationEntryPoint(new BasicAuthenticationEntryPointCustom()));
//        http.httpBasic(f -> f.disable());
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));
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


    @Deprecated
//    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();// will be used by Spring Security BCryptPasswordEncoder by default
        return delegatingPasswordEncoder;
    }

    @Deprecated
//    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        HaveIBeenPwnedRestApiPasswordChecker haveIBeenPwnedRestApiPasswordChecker = new HaveIBeenPwnedRestApiPasswordChecker();
        return haveIBeenPwnedRestApiPasswordChecker;
    }

    @Deprecated
//    @Bean
    public AuthenticationManager authenticationManager(UserDetailsServiceCustom userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        UsernamePwdAuthenticationProviderCustomProd usernamePwdAuthenticationProviderCustom =
                new UsernamePwdAuthenticationProviderCustomProd(userDetailsService, passwordEncoder);
        ProviderManager providerManager = new ProviderManager(usernamePwdAuthenticationProviderCustom);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }
}
