package com.example.openinview.controller;

import com.example.openinview.entity.User;
import com.example.openinview.repository.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/openinview")
@RequiredArgsConstructor
public class UserController {

    private final UserDataService userDataService;

    @GetMapping("/{id}")
    public String triggerException(@PathVariable Long id) {
        User user = userDataService.findById(id).orElseThrow();
        int postCount = user.getPosts().size(); // 💥 Boom! LazyInitializationException
        return "User has " + postCount + " posts.";
    }

}
