package com.example.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
//Purpose: Used to define a controller class that returns views
//(HTML pages), JSON/XML or other data.
//Return value: By default, return values are treated as view names.
public class ClientApplication {

    @GetMapping("/")
    public String home() {
        return "home";
    }


    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

}
