package com.example.actuator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sb-actuator")
public class MyController {

    @GetMapping("/test")
    public String testEndPoint() {
        return "This is some text.";
    }

}
