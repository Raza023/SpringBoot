package com.example.security.controller;

import com.example.security.business.UserBusiness;
import com.example.security.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@RestController
@RequestMapping("api/v1/security")
@RequiredArgsConstructor
public class UserController {

    private final UserBusiness userBusiness;

    @GetMapping("/{id}")
    public String getUserWithID(@PathVariable Long id) {
        UserDto userDto = userBusiness.getUserWithID(id);
        if (ObjectUtils.isEmpty(userDto)) {
            return "User not found.";
        }
        // No LazyInitializationException here now, because we fetched data only in
        // @Transactional
        int postCount = userDto.getPosts().size();
        return "User has " + postCount + " posts.";
    }

    @GetMapping("/all")
    public List<UserDto> getAllUsersWithPosts() {
        return userBusiness.getAllUsersWithPosts();
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userBusiness.getAllUsers();
    }

    @PostMapping("/add-dummy-users")
    public String addDummyUsers() {
        int count = userBusiness.addDummyUsers(100);
        return count + " dummy users added.";
    }

}
