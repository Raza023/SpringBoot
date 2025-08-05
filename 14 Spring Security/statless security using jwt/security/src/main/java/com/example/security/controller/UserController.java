package com.example.security.controller;

import com.example.security.business.UserBusiness;
import com.example.security.config.CustomUserDetails;
import com.example.security.jwt.util.JwtUtil;
import com.example.security.model.UserDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
@Slf4j
public class UserController {

    private final UserBusiness userBusiness;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/welcome") // by passed security
    public String getGreeting() {
        return "Welcome to spring boot security.";
    }

    @GetMapping("/{id}")
    // It's a DAP check, data is accessible only for current logged in user where id
    // == principal.id
    // #id == principal.id is called "SpEL (Spring Expression Language)"
    @PreAuthorize("#id == principal.id") // We don't need to specify @PreAuthorize("hasAuthority('ROLE_USER')") here.
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

    // It is similar to above function.
    @GetMapping("/auth-argument/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String getUserWithID(@PathVariable Long id, Authentication authentication) {
        // It's a DAP check, data is accessible only for current logged-in user where id
        // == principal.id
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        if (!principal.getId().equals(id)) {
            throw new AccessDeniedException("Not authorized");
        }
        UserDto userDto = userBusiness.getUserWithID(id);
        if (ObjectUtils.isEmpty(userDto)) {
            return "User not found.";
        }
        // No LazyInitializationException here now, because we fetched data only in
        // @Transactional
        int postCount = userDto.getPosts().size();
        return "User has " + postCount + " posts.";
    }

    /**
     * You cannot automatically add a token to a new request (/all) from the backend unless you're making the call
     * yourself. The client (e.g. browser, Postman, frontend) must send the token for secure APIs like /all.
     *
     * @return List of UserDto
     */
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserDto> getAllUsersWithPost() {
        return userBusiness.getAllUsersWithPosts();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_HR')")
    public List<UserDto> getAllUsers() {
        return userBusiness.getAllUsers();
    }

    @PostMapping("/add-dummy-users") // by passed security
    public String addDummyUsers() {
        int count = userBusiness.addDummyUsers(100);
        return count + " dummy users added.";
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody UserDto userDto) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getName(), userDto.getPassword()));
        } catch (Exception ex) {
            throw new Exception("invalid username/password");
        }
        String token = jwtUtil.generateToken(userDto.getName());
        log.info("JWT: " + token);
        UserDetails userDetails = new User(userDto.getName(), userDto.getPassword(), new ArrayList<>());
        log.debug("Valid: " + jwtUtil.validateToken(token, userDetails));
        log.debug("Username: " + jwtUtil.extractUsername(token));
        return token;
    }

}
