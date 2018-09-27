package com.example.gateway.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Base64;

public class TokenAuthenticationFitler extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;

    public TokenAuthenticationFitler(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        System.out.println("Token testing: "+authorization);
        if (authorization == null || !authorization.startsWith("Token ")) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication newToken = new TokenAuthenticationToken(authorization.substring(6));

        Authentication authenticated = this.authenticationManager.authenticate(newToken);
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        // TODO: need we still do the filter chain even if we have created token?
        filterChain.doFilter(request, response);
    }
}
