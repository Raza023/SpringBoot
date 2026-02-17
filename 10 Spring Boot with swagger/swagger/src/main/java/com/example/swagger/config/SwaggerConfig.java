package com.example.swagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@OpenAPIDefinition(
//        info = @io.swagger.v3.oas.annotations.info.Info(
//                title = "Book Service API",
//                version = "1.0",
//                description = "API documentation for the Book Service",
//                license = @io.swagger.v3.oas.annotations.info.License(
//                        name = "Java Learning License", url = "https://github.com/Raza023/SpringBoot")
//        )
//)
public class SwaggerConfig {

    // Grouped OpenAPI for specific endpoints (e.g., book API)
    @Bean
    public GroupedOpenApi bookApi() {
        return GroupedOpenApi.builder()
                .group("Book API")  // Group name
                .pathsToMatch("/api/v1/books/**")  // Match paths for the book API
                .build();
    }

    // Grouped OpenAPI for other endpoints (e.g., person API)
    @Bean
    public GroupedOpenApi personApi() {
        return GroupedOpenApi.builder()
                .group("Person API")  // Group name
                .pathsToMatch("/api/v1/person/**")  // Match paths for the person API
                .build();
    }

    // Global OpenAPI configuration (metadata)
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Book Service")
                        .description("Sample Documentation Generated Using OpenAPI for the Book Rest API")
                        .version("1.0")
                        .termsOfService("https://github.com/Raza023")
                        .license(new License().name("Java Learning License")
                                .url("https://github.com/Raza023/SpringBoot")));
    }
}