package com.example.gateway.repository;

import com.example.gateway.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    public List<UserRole> findByUserId(String userId);
}
