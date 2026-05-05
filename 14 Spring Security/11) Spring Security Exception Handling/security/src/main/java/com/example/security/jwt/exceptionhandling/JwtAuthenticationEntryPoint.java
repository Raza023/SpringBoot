package com.example.security.jwt.exceptionhandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Unauthorized access (no token or invalid token) handled by a custom AuthenticationEntryPoint. Handles unauthorized
 * requests (401 errors) — like missing or invalid token.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String reason = (String) request.getAttribute(RequestAttributes.JWT_EXCEPTION);
        String message = switch (reason != null ? reason : "default") {
            case "expired" -> "JWT token has expired";
            case "invalid_signature" -> "Invalid JWT signature";
            case "invalid_token" -> "Malformed or invalid token";
            default -> "Authentication required - Token may be missing or invalid";
        };

        String traceId = (String) request.getAttribute(RequestAttributes.TRACE_ID);
        ErrorResponse error = new ErrorResponse(
                401,
                "Unauthorized",
                message,
                request.getRequestURI(),
                traceId
        );
        response.getWriter().write(mapper.writeValueAsString(error));
    }
}