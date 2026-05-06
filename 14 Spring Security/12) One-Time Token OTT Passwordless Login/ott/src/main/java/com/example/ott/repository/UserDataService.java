package com.example.ott.repository;

import com.example.ott.modal.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataService extends JpaRepository<User, Long> {

    Optional<User> findByName(String username);

}
