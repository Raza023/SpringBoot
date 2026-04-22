package com.example.security.jwt.exceptionhandling;

import org.springframework.stereotype.Component;

/**
 * Unauthorized access (no token or invalid token) handled by a custom AuthenticationEntryPoint. Handles unauthorized
 * requests (401 errors) — like missing or invalid token.
 */
@Component
public class JwtAuthenticationEntryPoint {}
//implements AuthenticationEntryPoint {
//
//    @Override
//    public void commence(HttpServletRequest request,
//            HttpServletResponse response,
//            AuthenticationException authException)
//            throws IOException, ServletException {
//
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setContentType("application/json");
//        response.getWriter().write("{\"error\": \"Unauthorized - Token may be missing or invalid\"}");
//    }
//}