package com.example.ldap.controller;

import com.example.ldap.business.UserBusiness;
import com.example.ldap.entity.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ldap")
@RequiredArgsConstructor
public class UserController {

    private final UserBusiness userBusiness;

    @GetMapping("/secure")
    public String secureMethod() {
        return "secure rest endpoint";
    }

    @GetMapping("/{id}")
    public String getUserWithID(@PathVariable Long id) {
        UserDto userDto = userBusiness.getUserWithID(id);
        if (ObjectUtils.isEmpty(userDto)) {
            return "User not found.";
        }
        // No LazyInitializationException here now, because we fetched data only in @Transactional
        int postCount = userDto.getPosts().size();
        return "User has " + postCount + " posts.";
    }

}
