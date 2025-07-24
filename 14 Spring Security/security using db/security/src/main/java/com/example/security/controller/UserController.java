package com.example.security.controller;

import com.example.security.business.UserBusiness;
import com.example.security.config.CustomUserDetails;
import com.example.security.model.User;
import com.example.security.model.UserDto;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    //It's a DAP check, data is accessible only for current logged in user where id == principal.id
    //#id == principal.id is called "SpEL (Spring Expression Language)"
    @PreAuthorize("#id == principal.id")
    //We don't need to specify @PreAuthorize("hasAuthority('ROLE_USER')") here. We can specify for added check of FAP.
    // like @PreAuthorize("hasAuthority('ROLE_USER') and #id == principal.id")
    //If users can only access their own data, the role doesn’t matter, what matters is whether the id matches or not.
    public String getUserWithID(@PathVariable Long id) {
        UserDto userDto = userBusiness.getUserWithID(id);
        if (ObjectUtils.isEmpty(userDto)) {
            return "User not found.";
        }
        // No LazyInitializationException here now, because we fetched data only in @Transactional
        int postCount = userDto.getPosts().size();
        return "User has " + postCount + " posts.";
    }

    //It is similar to above function.
    @GetMapping("/auth-argument/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String getUserWithID(@PathVariable Long id, Authentication authentication) {
        //It's a DAP check, data is accessible only for current logged-in user where id == principal.id
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        if (!principal.getId().equals(id)) {
            throw new AccessDeniedException("Not authorized");
        }
        UserDto userDto = userBusiness.getUserWithID(id);
        if (ObjectUtils.isEmpty(userDto)) {
            return "User not found.";
        }
        // No LazyInitializationException here now, because we fetched data only in @Transactional
        int postCount = userDto.getPosts().size();
        return "User has " + postCount + " posts.";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserDto> getAllUsersWithPosts() {
        return userBusiness.getAllUsersWithPosts();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_HR')")
    public List<UserDto> getAllUsers() {
        return userBusiness.getAllUsers();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_HR')")
    public String addNewUser(@RequestBody User user) throws Exception {
        User addedUser = userBusiness.addNewUser(user);
        if (Objects.nonNull(addedUser)) {
            return "New user added.";
        }
        return "There was an error in adding user";
    }

    @PostMapping("/add-dummy-users")   //by passed security
    public String addDummyUsers() {
        int count = userBusiness.addDummyUsers(100);
        return count + " dummy users added.";
    }

}
