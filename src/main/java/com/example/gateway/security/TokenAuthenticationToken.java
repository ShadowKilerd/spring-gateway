package com.example.gateway.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class TokenAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public TokenAuthenticationToken(String credentials) {
        super(null, credentials);
    }

    public TokenAuthenticationToken(String principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
