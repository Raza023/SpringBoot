package com.example.security;

import com.example.security.model.Post;
import com.example.security.model.User;
import com.example.security.repository.UserDataService;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setName("admin");
        user1.setPassword(passwordEncoder.encode("admin"));
        user1.setRoles(Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
        List<Post> posts1 = new ArrayList<>();
        Post post1 = new Post();
        post1.setTitle("post 1 title of user1");
        post1.setUser(user1);
        posts1.add(post1);
        user1.setPosts(posts1);
//        RefreshToken refreshToken = new RefreshToken();
//        refreshToken.setToken(UUID.randomUUID().toString());
//        refreshToken.setExpiryDate(Instant.now());
//        refreshToken.setUser(user1);
//        user1.setRefreshToken(refreshToken);
        users.add(user1);

        User user2 = new User();
        user2.setName("Hassan");
        user2.setPassword(passwordEncoder.encode("Hassan"));
        user2.setRoles(Arrays.asList("ROLE_ADMIN", "ROLE_HR", "ROLE_USER"));
        List<Post> posts2 = new ArrayList<>();
        Post post2 = new Post();
        post2.setTitle("post 2 title of user2");
        post2.setUser(user2);
        posts2.add(post2);
        user2.setPosts(posts2);
//        RefreshToken refreshToken2 = new RefreshToken();
//        refreshToken2.setToken(UUID.randomUUID().toString());
//        refreshToken2.setExpiryDate(Instant.now());
//        refreshToken2.setUser(user2);
//        user2.setRefreshToken(refreshToken2);
        users.add(user2);

        User user3 = new User();
        user3.setName("Hussain");
        user3.setPassword(passwordEncoder.encode("Hussain"));
        user3.setRoles(Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
        List<Post> posts3 = new ArrayList<>();
        Post post3 = new Post();
        post3.setTitle("post 3 title of user3");
        post3.setUser(user3);
        posts3.add(post3);
        user3.setPosts(posts3);
//        RefreshToken refreshToken3 = new RefreshToken();
//        refreshToken3.setToken(UUID.randomUUID().toString());
//        refreshToken3.setExpiryDate(Instant.now());
//        refreshToken3.setUser(user3);
//        user3.setRefreshToken(refreshToken3);
        users.add(user3);

        User user4 = new User();
        user4.setName("Amir");
        user4.setPassword(passwordEncoder.encode("Amir"));
        user4.setRoles(List.of("ROLE_USER"));
        List<Post> posts4 = new ArrayList<>();
        Post post4 = new Post();
        post4.setTitle("post 4 title of user4");
        post4.setUser(user4);
        posts4.add(post4);
        user4.setPosts(posts4);
//        RefreshToken refreshToken4 = new RefreshToken();
//        refreshToken4.setToken(UUID.randomUUID().toString());
//        refreshToken4.setExpiryDate(Instant.now());
//        refreshToken4.setUser(user4);
//        user4.setRefreshToken(refreshToken4);
        users.add(user4);

        User user5 = new User();
        user5.setName("Babar");
        user5.setPassword(passwordEncoder.encode("Babar"));
        user5.setRoles(List.of("ROLE_HR"));
        List<Post> posts5 = new ArrayList<>();
        Post post5 = new Post();
        post5.setTitle("post 5 title of user5");
        post5.setUser(user5);
        posts5.add(post5);
        user5.setPosts(posts5);
//        RefreshToken refreshToken5 = new RefreshToken();
//        refreshToken5.setToken(UUID.randomUUID().toString());
//        refreshToken5.setExpiryDate(Instant.now());
//        refreshToken5.setUser(user5);
//        user5.setRefreshToken(refreshToken5);
        users.add(user5);
        userDataService.saveAllAndFlush(users);
    }

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

}
