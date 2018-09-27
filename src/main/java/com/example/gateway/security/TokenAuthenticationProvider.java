package com.example.gateway.security;

import com.example.gateway.entity.Token;
import com.example.gateway.entity.User;
import com.example.gateway.entity.UserRole;
import com.example.gateway.repository.TokenRepository;
import com.example.gateway.repository.UserRepository;
import com.example.gateway.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = authentication.getCredentials().toString();
        Token fetchedToken = tokenRepository.findByToken(token);
        if(fetchedToken == null) {
            throw new BadCredentialsException("invalid token");
        }


        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

        for(UserRole userRole: userRoleRepository.findByUserId(fetchedToken.getUserId())) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(userRole.getRole()));
        }

        return new TokenAuthenticationToken(fetchedToken.getUserId(), null, simpleGrantedAuthorities);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
