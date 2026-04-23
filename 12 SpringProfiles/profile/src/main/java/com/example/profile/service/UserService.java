package com.example.profile.service;

import com.example.profile.dao.UserRepository;
import com.example.profile.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(value = {"local", "dev", "prod"})
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAllUsers();
    }
}
