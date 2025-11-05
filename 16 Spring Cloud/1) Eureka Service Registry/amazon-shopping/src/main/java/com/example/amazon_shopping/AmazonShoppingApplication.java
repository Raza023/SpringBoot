package com.example.amazon_shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AmazonShoppingApplication {

	//Multiple apps can access our microservice, @LoadBalance will handle that cluster thing.
	@LoadBalanced
	@Bean
	public RestTemplate template() {
		return new RestTemplate();
	}

	/**
	 * But if your RestTemplate bean is annotated with @LoadBalanced,
	 * then it treats localhost as a service name (to look up from Eureka), not a literal host.
	 * Why This Happens
	 * If you have @LoadBalanced:
	 * That @LoadBalanced makes the RestTemplate resolve hostnames through Eureka.
	 * So, "http://localhost:8585/..." is treated as “find the service named localhost from the registry.”.
	 *
	 * @return RestTemplate
	 */
	@Bean
	public RestTemplate plainRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(AmazonShoppingApplication.class, args);
	}

}
