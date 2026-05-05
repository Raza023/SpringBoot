package com.example.security.jwt.exceptionhandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * Access denied (valid token, but lacking roles) handled by a custom AccessDeniedHandler. Handles forbidden access (403
 * errors) — user is authenticated but not authorized.
 */
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        String traceId = (String) request.getAttribute(RequestAttributes.TRACE_ID);
        ErrorResponse error = new ErrorResponse(
                403,
                "Forbidden",
                "Forbidden - You don't have permission to access this resource",
                request.getRequestURI(),
                traceId);
        response.getWriter().write(mapper.writeValueAsString(error));
    }
}