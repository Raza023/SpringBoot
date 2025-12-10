package com.example.security.repository;

import com.example.security.model.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenDataService extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String refreshToken);

    Optional<RefreshToken> findByUserId(Long id);
}
