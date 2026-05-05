package com.example.security.jwt.exceptionhandling;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TraceIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String traceId = UUID.randomUUID().toString();

        // store in request
        request.setAttribute(RequestAttributes.TRACE_ID, traceId);

        // store in logs (MDC)
        MDC.put(RequestAttributes.TRACE_ID, traceId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(RequestAttributes.TRACE_ID);
        }
    }
}