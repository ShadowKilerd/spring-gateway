package com.example.gateway.security;


import com.example.gateway.entity.User;
import com.example.gateway.entity.UserRole;
import com.example.gateway.repository.UserRepository;
import com.example.gateway.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class CustomBasicAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("invalid user information");
        }

        if (passwordEncoder.matches(password, user.getPassword())) {

//            user.getRoles().stream().map((userRole -> userRole.getRole())).collect(List.class)
            List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

            for(UserRole userRole: userRoleRepository.findByUserId(user.getId())) {
                simpleGrantedAuthorities.add(new SimpleGrantedAuthority(userRole.getRole()));
            }

            return new CustomUsernamePasswordAuthenticationToken(user.getId(), null, simpleGrantedAuthorities);
        }

        throw new BadCredentialsException("invalid password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
