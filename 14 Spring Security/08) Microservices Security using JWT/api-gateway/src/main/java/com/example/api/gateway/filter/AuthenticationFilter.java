package com.example.api.gateway.filter;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator validator;
    private final RestTemplate restTemplate;

    private String extractUsernameFromToken(String token) {
        try {
            String[] chunks = token.split("\\.");
            String payload = new String(java.util.Base64.getUrlDecoder().decode(chunks[1]));
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.readTree(payload).get("sub").asText();   // or "username"
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT Token: " + e.getMessage());
        }
    }


    @Override
    public GatewayFilter apply(AuthenticationFilter.Config config) {

        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //Header contains token or not.
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("AUTHORIZATION Header is missing.");
                }
                String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).getFirst();
                if (StringUtils.isNotEmpty(token) && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                try {
                    String username = extractUsernameFromToken(token);
                    // Call to REST client
                    Boolean response = restTemplate.getForObject("http://IDENTITY-SERVICE/validate/"+username+"?token=", Boolean.class);
                    // Process response
                } catch (HttpClientErrorException e) {
                    // Handle 4xx client errors (e.g., 404 Not Found, 400 Bad Request)
                    System.err.println("Client error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
                } catch (HttpServerErrorException e) {
                    // Handle 5xx server errors (e.g., 500 Internal Server Error)
                    System.err.println("Server error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
                } catch (RestClientException e) {
                    // Handle other RestTemplate-specific exceptions (e.g., network issues, serialization errors)
                    System.err.println("REST client error: " + e.getMessage());
                } catch (Exception e) {
                    // Catch any other unexpected exceptions
                    System.err.println("An unexpected error occurred: " + e.getMessage());
                }

            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
