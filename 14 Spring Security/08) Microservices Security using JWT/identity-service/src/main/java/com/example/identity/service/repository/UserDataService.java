package com.example.identity.service.repository;


import com.example.identity.service.entity.UserInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataService extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> findByName(String username);

}
