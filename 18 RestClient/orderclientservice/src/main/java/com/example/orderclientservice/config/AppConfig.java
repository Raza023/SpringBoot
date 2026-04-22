package com.example.orderclientservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
public class AppConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8081")
                .requestFactory(
                        new JdkClientHttpRequestFactory(
                                HttpClient.newBuilder()
                                        .connectTimeout(Duration.ofSeconds(5))
                                        .build()
                        )
                )
                .build();
    }

}