package com.example.gateway.repository;

import com.example.gateway.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {

    public Token findByToken(String token);
}
