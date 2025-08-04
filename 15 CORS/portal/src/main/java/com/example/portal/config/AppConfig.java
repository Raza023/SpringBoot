package com.example.portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig {
    @Bean
    public WebMvcConfigurer configure() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins("http://localhost:9090");
                //for all the endpoints (/*) allowed origin is http://localhost:9090

                //registry.addMapping("/*").allowedOrigins("*");
                //for all the endpoints (/*) allowed origin is any domain
            }

        };
    }


}
