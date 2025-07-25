package com.example.security.repository;

import com.example.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDataService extends JpaRepository<Role, Integer> {

    Optional<Role> findByRole(String role);

}
