package com.example.security.repository;

import com.example.security.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDataService extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.posts WHERE u.id = :id")
    Optional<User> findByIdWithPosts(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findByIdWithOutPosts(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findByIdWithoutPosts(@Param("id") Long id);


    @Query(value = "SELECT * from Users WHERE ID = :id", nativeQuery = true)
    Optional<User> findByIdWithOutPostsUsingNativeQuery(@Param("id") Long id);

}
