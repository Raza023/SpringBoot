package com.example.identity.service.controller;

import com.example.identity.service.business.AuthBusiness;
import com.example.identity.service.dto.AuthRequest;
import com.example.identity.service.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthBusiness authBusiness;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return authBusiness.saveUser(userInfo);
    }

    @PostMapping("/token")
    public String createNewToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                return authBusiness.generateToken(authRequest.getUsername());
            } else {
                log.error("Username or Password is not valid.");
                return "Username or Password is not valid.";
            }
        } catch (Exception e) {
            throw new RuntimeException("Username or Password is not valid. Exception is: " + e.getMessage());
        }
    }

    @GetMapping("/validate/{username}")
    public Boolean validateToken(@RequestParam("token") String token, @PathVariable String username) {
        return authBusiness.validateToken(token, username);
    }

}
