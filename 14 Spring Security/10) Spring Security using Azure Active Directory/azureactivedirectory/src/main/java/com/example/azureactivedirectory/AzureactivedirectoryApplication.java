package com.example.azureactivedirectory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/api/v1")
public class AzureactivedirectoryApplication {

    @GetMapping("/login")
    public String welcomeScreen() {
        return "Welcome to our application, you are successfully logged in!!!";
    }

    public static void main(String[] args) {
        SpringApplication.run(AzureactivedirectoryApplication.class, args);
    }

}
