package com.example.gateway.repository;

import com.example.gateway.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    public User findByUsername(String username);

}
