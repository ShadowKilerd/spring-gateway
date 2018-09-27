package com.example.gateway.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Base64;

public class CustomBasicAuthenticationFilter extends OncePerRequestFilter {
    private AuthenticationManager authenticationManager;

    public CustomBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        System.out.println("basic testing: " + authorization);
        if (authorization == null || !authorization.startsWith("Basic ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token;
        try {
            byte[] base64Token = authorization.substring(6).getBytes("UTF-8");
            byte[] decoded = Base64.getDecoder().decode(base64Token);

            token = URLDecoder.decode(new String(decoded, "UTF-8"), "UTF-8");
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");

        }

        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        Authentication newToken = new CustomUsernamePasswordAuthenticationToken(token.substring(0, delim), token.substring(delim + 1));

        Authentication authenticated = this.authenticationManager.authenticate(newToken);
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        // TODO: need we still do the filter chain even if we have created token?
        filterChain.doFilter(request, response);
    }
}
