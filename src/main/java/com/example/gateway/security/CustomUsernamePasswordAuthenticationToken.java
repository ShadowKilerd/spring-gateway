package com.example.gateway.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public CustomUsernamePasswordAuthenticationToken(String principal, String credentials) {
        super(principal, credentials);
    }

    public CustomUsernamePasswordAuthenticationToken(String principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
