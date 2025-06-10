package com.example.openinview.repository;

import com.example.openinview.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDataService extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN u.posts WHERE u.id = :id")
    Optional<User> findByIdWithPosts(@Param("id") Long id);

}
