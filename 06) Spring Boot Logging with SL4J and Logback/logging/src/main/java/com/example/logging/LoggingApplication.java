package com.example.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class LoggingApplication {

    //In this tutorial we learnt three ways for logging:
    //1) Using application.properties (it creates api.log and logs on console)
    //2) Using application.yaml (it creates api.log and logs on console)
    //3) using logback.xml (it creates logs/debug.xml and logs/error.log and logs on console)
    Logger log = LoggerFactory.getLogger(LoggingApplication.class);

    @GetMapping("/test/{name}")
    public String greeting(@PathVariable String name) {
        String response = "";
        try {
            if (name.equalsIgnoreCase("test")) {
                throw new RuntimeException(name+" is not a name...");
            }
            log.debug("Request {}", name);
            response = "Hi " + name + "! Welcome to Lahore.";
            log.debug("Response {}", response);
        } catch (Exception e) {
            log.error("An exception occurred", e);
            response = "Error: " + e.getMessage();  // Return the exception message
        } finally {
            response = "An exception occurred: "+response;
        }
        return response;
    }

    public static void main(String[] args) {
        SpringApplication.run(LoggingApplication.class, args);
    }

}
