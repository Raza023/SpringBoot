package com.example.jsondocs;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJSONDoc
public class JsonDocsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsonDocsApplication.class, args);
	}

}
