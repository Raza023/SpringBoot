package com.example.security;

import com.example.security.model.User;
import com.example.security.repository.UserDataService;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class SecurityApplication {

    private final UserDataService userDataService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initUsers() {
        List<User> users = Stream.of(
                new User(null, "hassan", passwordEncoder.encode("password"),
                        new ArrayList<>(Arrays.asList("ROLE_ADMIN", "ROLE_HR")), new ArrayList<>()),
                new User(null, "user1", passwordEncoder.encode("password1"),
                        new ArrayList<>(List.of("ROLE_ADMIN")), new ArrayList<>()),
                new User(null, "user2", passwordEncoder.encode("password2"),
                        new ArrayList<>(List.of("ROLE_USER")), new ArrayList<>()),
                new User(null, "user3", passwordEncoder.encode("password3"),
                        new ArrayList<>(List.of("ROLE_HR")), new ArrayList<>())
        ).collect(Collectors.toList());
        userDataService.saveAllAndFlush(users);
    }

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

}
