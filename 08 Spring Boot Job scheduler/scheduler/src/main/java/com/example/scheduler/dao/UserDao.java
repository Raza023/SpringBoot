package com.example.scheduler.dao;

import com.example.scheduler.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {
}
