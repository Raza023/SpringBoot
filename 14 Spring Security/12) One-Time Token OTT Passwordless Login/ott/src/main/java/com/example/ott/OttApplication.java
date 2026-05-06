package com.example.ott;

import com.example.ott.modal.User;
import com.example.ott.repository.UserDataService;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class OttApplication {

    private final PasswordEncoder passwordEncoder;
    private final UserDataService userDataService;

    @PostConstruct
    public void initUsers() {
        List<User> users = Stream.of(
                new User(null, "Hassan Raza", "hassan", "hassan@gmail.com", passwordEncoder.encode("password1"),
                        new ArrayList<>(Arrays.asList("ROLE_ADMIN", "ROLE_HR"))),
                new User(null, "Hussain Raza", "hussain", "hussain@gmail.com", passwordEncoder.encode("password2"),
                        new ArrayList<>(List.of("ROLE_ADMIN"))),
                new User(null, "Ali Raza", "ali", "ali@gmail.com", passwordEncoder.encode("password3"),
                        new ArrayList<>(List.of("ROLE_HR"))),
                new User(null, "Abbas Ali", "abbas", "abbas@gmail.com", passwordEncoder.encode("password4"),
                        new ArrayList<>(List.of("ROLE_USER")))
        ).toList();
        userDataService.saveAllAndFlush(users);
    }

    public static void main(String[] args) {
        SpringApplication.run(OttApplication.class, args);
    }

}
