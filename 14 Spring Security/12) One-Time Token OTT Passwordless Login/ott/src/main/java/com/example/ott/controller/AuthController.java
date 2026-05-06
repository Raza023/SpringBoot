package com.example.ott.controller;

import com.example.ott.modal.User;
import com.example.ott.repository.UserDataService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final UserDataService userDataService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public String signup(@RequestParam String name, @RequestParam String username, @RequestParam String email,
            @RequestParam String password) {
        User user = new User(null, name, username, email, passwordEncoder.encode(password),
                new ArrayList<>(List.of("ROLE_USER")));
        userDataService.save(user);
        return "redirect:/api/v1/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/index")
    public String home(Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("user", principal.getName());
        }
        return "index";
    }

//    @GetMapping("/ott-sent")
//    public String ottSent() {
//        return "ott-sent";
//    }

//    @PostMapping("/ott-sent")
//    public String ottSent(@RequestParam String username, Model model) {
//        model.addAttribute("username", username);
//        return "ott-sent";
//    }

//    @PostMapping("/login-ott")
//    public String sendToken(@RequestParam String username) {
//        // generate token + email it
//        return "redirect:/api/v1/ott-sent";
//    }

//    @PostMapping("/login-ott")
//    public String loginOttPage(@RequestParam String username) {
//        return "login-ott";
//    }

}
