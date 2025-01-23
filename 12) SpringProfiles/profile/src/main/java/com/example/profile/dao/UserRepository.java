package com.example.profile.dao;

import com.example.profile.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE LOWER(u.name) = LOWER(:name)")
    User findByName(@Param("name") String name);

    @Query(value = "SELECT u FROM User u")
    List<User> findAllUsers();

}
