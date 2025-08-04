package com.example.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//-------------------Class level Annotation-------------------
//@CrossOrigin(origins="http://localhost:9090")
//it will allow the above client or URL to access all the endpoints of this controller.
//@CrossOrigin(origins="*")
//it will allow all clients or URLs to access all the endpoints of this controller.
@SpringBootApplication
@RestController
public class PortalApplication {

	//-------------------Method level Annotation-------------------
	//@CrossOrigin(origins="http://localhost:9090")
	//it will allow the above client or URL to access this endpoint.
	//@CrossOrigin(origins="*")
	//it will allow all clients or URLs to access this endpoint.
	@GetMapping("/access")
	public String greeting() {
		return "Welcome to my new portal";
	}

	public static void main(String[] args) {
		SpringApplication.run(PortalApplication.class, args);
	}

}
