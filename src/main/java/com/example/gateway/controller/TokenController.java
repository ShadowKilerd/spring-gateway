package com.example.gateway.controller;

import com.example.gateway.entity.Token;
import com.example.gateway.entity.User;
import com.example.gateway.repository.TokenRepository;
import com.example.gateway.repository.UserRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Random;

@RestController
public class TokenController {

    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping("/user/token")
    public Token generateUserToken() {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Token token = new Token();
        token.setUserId(userId);
        String randomToken = randomString(40);
        token.setToken(randomToken);

        return tokenRepository.save(token);

    }

    private String randomString(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
