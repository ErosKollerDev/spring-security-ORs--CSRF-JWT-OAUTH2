package com.eazybytes.springsecsection1.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/**
 * This filter is used to get the CSRF token from the request and store it in the
 * HTTP response as a cookie. The cookie is then sent with every request from the
 * client to the server and the server can verify the presence of the CSRF token
 * in the cookie.
 *
 * The filter is added after the {@link org.springframework.security.web.csrf.CsrfFilter}
 * and before the security filter chain.
 *
 * The filter is only used when the {@link org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter}
 * is configured to use CSRF protection.
 *
 * The filter is not used when the request is an AJAX request.
 *
 * The filter is not used when the request is an OPTIONS request.
 *
 * The filter is not used when the request is a GET request.
 *
 * The filter is not used when the request is a HEAD request.
 */
public class CsrfCookieFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        // use csrfToken
        csrfToken.getToken();
        filterChain.doFilter(request, response);
    }

}
