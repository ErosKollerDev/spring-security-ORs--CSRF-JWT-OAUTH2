package com.eazybytes.springsecsection1.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenAuthenticationConverter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeyCloakOpaqueConverter implements OpaqueTokenAuthenticationConverter {
    @Override
    public Authentication convert(String introspectedToken, OAuth2AuthenticatedPrincipal authenticatedPrincipal) {
        String username = authenticatedPrincipal.getAttribute("preferred_username");
        Map<String, Object> realmAccess = authenticatedPrincipal.getAttribute("realm_access");
        List<String> roles = (List<String>) realmAccess.get("roles");
        List<SimpleGrantedAuthority> rolesList = roles.stream().map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(username, null, rolesList);
    }
}
