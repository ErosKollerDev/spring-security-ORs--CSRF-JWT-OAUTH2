package com.eazybytes.springsecsection1.config;

import com.eazybytes.springsecsection1.exceptionhandling.AccessDeniedHandlerCustom;
import com.eazybytes.springsecsection1.exceptionhandling.BasicAuthenticationEntryPointCustom;
import com.eazybytes.springsecsection1.filter.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("!prod")
public class ProjectSecurityConfigCustom {

//    @Value("${spring.security.oauth2.resourceserver.opaque.introspection-uri}")
//    String introspectionUri;
//
//    @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-id}")
//    String clientId;
//
//    @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-secret}")
//    String clientSecret;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {


        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakRoleConverter());

        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        csrfTokenRequestAttributeHandler.setCsrfRequestAttributeName("XSRF-TOKEN");

        http

//                Redirect to a custom page if the session is invalid
                /* not required when working with stateless session jwt token */
//                .securityContext(securityContextConfiguration -> securityContextConfiguration.requireExplicitSave(false))
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .maximumSessions(300)
                        .maxSessionsPreventsLogin(true))
                .requiresChannel(requestChannelConfiguration ->
                        requestChannelConfiguration.anyRequest().requiresInsecure())// only HTTP
                .csrf(csrfConfig -> csrfConfig
                        .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/contact", "/contact-all", "/register", "/login/**"))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
//                .addFilterBefore(new RequestValidationEmailBeforeBasicFilter(), BasicAuthenticationFilter.class)
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
                .authorizeHttpRequests((requests) -> {
//                    requests.anyRequest()
//                            .permitAll();
//                            .denyAll();
//                        .authenticated()
                            requests
                                    .requestMatchers("/admin/**").authenticated()
//                                    .requestMatchers("/my/**").authenticated()
//                                    .requestMatchers("/my/account").hasAnyAuthority("VIEWACCOUNT", "MASTER_OF_THE_UNIVERSE", "ADMIN")
                                    .requestMatchers("/my/account").hasAnyRole("ADMIN", "USER")
//                                    .requestMatchers("/my/account").hasRole("ADMIN")
//                                    .requestMatchers("/my/cards").hasAnyAuthority("VIEWCARDS", "MASTER_OF_THE_UNIVERSE", "ADMIN")
                                    .requestMatchers("/my/cards").hasAnyRole("ADMIN", "USER")
//                                    .requestMatchers("/my/loans").hasAnyAuthority("VIEWLOANS", "MASTER_OF_THE_UNIVERSE", "ADMIN")
                                    .requestMatchers("/my/loans").hasAnyRole("ADMIN", "USER")
//                                    .requestMatchers("/my/loans").authenticated()
//                                    .requestMatchers("/my/balance").hasAnyAuthority("VIEWBALANCE", "VIEWACCOUNT", "MASTER_OF_THE_UNIVERSE", "ADMIN")
                                    .requestMatchers("/my/balance").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers("/contact", "/contact-all", "/notices", "/error", "/register", "/invalid-session", "/login/**").permitAll();
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
//        http.oauth2ResourceServer(oauth2 -> oauth2.opaqueToken(opaqueToken ->
//                opaqueToken.authenticationConverter(new KeyCloakOpaqueConverter())
//                        .introspectionUri(introspectionUri)
//                        .introspectionClientCredentials(clientId, clientSecret)
//        ));
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
        UsernamePwdAuthenticationProviderCustom usernamePwdAuthenticationProviderCustom =
                new UsernamePwdAuthenticationProviderCustom(userDetailsService, passwordEncoder);
        ProviderManager providerManager = new ProviderManager(usernamePwdAuthenticationProviderCustom);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }
}
