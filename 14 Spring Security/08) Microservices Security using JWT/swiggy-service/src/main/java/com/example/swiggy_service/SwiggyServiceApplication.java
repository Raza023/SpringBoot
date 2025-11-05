package com.example.swiggy_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SwiggyServiceApplication {

    @Bean
    @LoadBalanced
    public RestTemplate template() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(SwiggyServiceApplication.class, args);
    }

}
