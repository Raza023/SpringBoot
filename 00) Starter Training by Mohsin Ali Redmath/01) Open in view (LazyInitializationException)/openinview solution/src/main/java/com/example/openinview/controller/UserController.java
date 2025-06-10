package com.example.openinview.controller;

import com.example.openinview.business.UserBusiness;
import com.example.openinview.entity.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/openinview")
@RequiredArgsConstructor
public class UserController {

    private final UserBusiness userBusiness;

    @GetMapping("/{id}")
    public String getUserWithID(@PathVariable Long id) {
        UserDto user = userBusiness.getUserWithID(id);
        if (ObjectUtils.isEmpty(user)) {
            return "User not found.";
        }
        int postCount = user.getPosts().size(); // No LazyInitializationException here now.
        return "User has " + postCount + " posts.";
    }

}
