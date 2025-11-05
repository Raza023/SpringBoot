package com.example.identity.service.controller;

import com.example.identity.service.business.AuthBusiness;
import com.example.identity.service.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthBusiness authBusiness;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return authBusiness.saveUser(userInfo);
    }

    @GetMapping("/token/{username}")
    public String createNewToken(@PathVariable String username) {
        return authBusiness.generateToken(username);
    }

    @GetMapping("/validate/{username}")
    public Boolean validateToken(@RequestParam("token") String token, @PathVariable String username) {
        return authBusiness.validateToken(token, username);
    }

}
