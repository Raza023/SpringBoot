package com.example.api.gateway.filter;

import java.util.List;
import java.util.function.Predicate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

    public static final List<String> openApiEndPoints = List.of(
            "/auth/register",
            "/auth/token",
            "/eureka"
    );

    // Apart from above listed URIs, all URIs will be secured.
    public Predicate<ServerHttpRequest> isSecured = serverHttpRequest -> openApiEndPoints.stream()
            .noneMatch(uri -> serverHttpRequest.getURI().getPath().contains(uri));

}
