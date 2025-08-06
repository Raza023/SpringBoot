package com.example.security;

import com.example.security.business.UserBusiness;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SecurityApplication {

	private final UserBusiness userBusiness;

	@PostConstruct
	public void init() {
		userBusiness.addNewAdmin();
	}

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

}
