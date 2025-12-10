package com.example.security.jwt.filter;

import com.example.security.jwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Custom JWT authentication filter that intercepts every HTTP request once per request lifecycle and sets the
 * SecurityContext if the token is valid. The JwtAuthenticationFilter is a Spring-managed bean (annotated with
 * Component), so Spring will inject it for you. I mean, don't use it in constructor injection or by using Autowired.
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Step 1: Get the Authorization header
        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        // Step 2: Check if the header starts with "Bearer " and extract token, username
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        // Step 3: If username is found and user is not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from database
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // Validate the token against user details
            if (jwtUtil.validateToken(jwt, userDetails)) {
                // If valid, create authentication token
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                // Attach request-specific details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Set authentication in security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Step 4: Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
