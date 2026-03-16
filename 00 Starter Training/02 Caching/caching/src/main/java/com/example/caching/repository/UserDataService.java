package com.example.caching.repository;

import com.example.caching.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserDataService extends JpaRepository<User, Long> {

    Optional<User> findByName(String userName);

    Optional<User> findByEmail(String email);
}
