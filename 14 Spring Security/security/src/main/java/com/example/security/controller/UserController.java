package com.example.security.controller;

import com.example.security.business.UserBusiness;
import com.example.security.model.UserDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/security")
@RequiredArgsConstructor
public class UserController {

    private final UserBusiness userBusiness;

    @GetMapping("/welcome")   //by passed security
    public String getGreeting() {
        return "Welcome to spring boot security.";
    }

    @GetMapping("/{id}")
    //#id == authentication.principal.id
    //(It's a DAP check, data is accessible only for current logged in user, we don't need to specify hasAuthority('ROLE_USER') for it.)
    @PreAuthorize("#id == principal.id")
    //@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_HR')")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserDto> getAllUsersWithPosts() {
        return userBusiness.getAllUsersWithPosts();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserDto> getAllUsers() {
        return userBusiness.getAllUsers();
    }

    @PostMapping("/add-dummy-users")   //by passed security
    public String addDummyUsers() {
        int count = userBusiness.addDummyUsers(100);
        return count + " dummy users added.";
    }

}
