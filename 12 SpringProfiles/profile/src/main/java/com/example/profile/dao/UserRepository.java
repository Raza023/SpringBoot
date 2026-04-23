package com.example.profile.dao;

import com.example.profile.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE LOWER(u.name) = LOWER(:name)")
    User findByName(@Param("name") String name);

    @Query(value = "SELECT u FROM User u")
    List<User> findAllUsers();

}
