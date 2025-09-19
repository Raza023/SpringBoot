package com.example.facebook.repository;

import com.example.facebook.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDataService extends JpaRepository<User, Long> {

    Optional<User> findByName(String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.posts")
    List<User> findAllWithPosts();

}
