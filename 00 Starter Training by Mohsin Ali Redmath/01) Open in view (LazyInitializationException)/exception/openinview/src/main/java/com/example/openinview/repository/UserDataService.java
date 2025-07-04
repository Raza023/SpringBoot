package com.example.openinview.repository;

import com.example.openinview.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataService extends JpaRepository<User, Long> {



}
