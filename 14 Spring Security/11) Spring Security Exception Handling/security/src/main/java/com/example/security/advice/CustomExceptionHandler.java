package com.example.security.advice;

import com.example.security.jwt.exceptionhandling.ApiException;
import com.example.security.jwt.exceptionhandling.ErrorResponse;
import com.example.security.jwt.exceptionhandling.JwtAccessDeniedHandler;
import com.example.security.jwt.exceptionhandling.RequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

    private final JwtAccessDeniedHandler accessDeniedHandler;

    @ExceptionHandler(AuthorizationDeniedException.class)
    public void handleAuthorizationDenied(AuthorizationDeniedException ex, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        // delegate to your existing handler
        accessDeniedHandler.handle(request, response, new AccessDeniedException(ex.getMessage(), ex));
    }

    //Explicitly throwing ApiException with a specific status and message.
    //Example:
    //if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
    //    throw new ApiException("Invalid credentials", HttpStatus.UNAUTHORIZED);
    //}
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex, HttpServletRequest request) {
        log.warn("API exception: {}", ex.getMessage());
        return buildError(ex.getStatus(), ex.getMessage(), request, null);
    }

    // Handle Validation Errors (IMPORTANT)
    // If you use @Valid, you must handle this:
    //Example body:
    //{
    //  "email": "invalid-email", // Fails @Email
    //  "password": "123"        // Fails @Size(min=8)
    //}
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();
        return buildError(HttpStatus.BAD_REQUEST, "Validation failed - ", request, errors);
    }

    // Handle Validation Errors (@RequestParam, @PathVariable validation)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request, null);
    }

    // Invalid JSON Request like: { "name": "abc", }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson(
            HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.error("Malformed JSON request.", ex);
        return buildError(HttpStatus.BAD_REQUEST, "Malformed JSON request.", request, null);
    }

    // Wrong HTTP method
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {
        return buildError(HttpStatus.METHOD_NOT_ALLOWED, ex.getMethod() + " method not supported", request, null);
    }

    // Wrong param type like: GET /users?id=abc   // expected Long
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, "Invalid value for parameter: " + ex.getName(), request, null);
    }

    // 404 (wrong URL)
    //Spring Boot does NOT throw it unless configured.
    //Add this to application.properties:
    //spring.mvc.throw-exception-if-no-handler-found=true
    //spring.web.resources.add-mappings=false
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoHandlerFoundException ex, HttpServletRequest request) {
        log.error("404 Not Found: {}", request.getRequestURI(), ex);
        return buildError(HttpStatus.NOT_FOUND, "Resource not found", request, null);
    }

    // Custom Business Exception
    //Example:
    //if (userAccount.getBalance() < withdrawalAmount) {
    //    throw new RuntimeException("Insufficient funds");
    //}
    //@ExceptionHandler(RuntimeException.class)
    //    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
    //        ErrorResponse error = new ErrorResponse(
    //                HttpStatus.BAD_REQUEST.value(),
    //                "Bad Request",
    //                ex.getMessage(),
    //                request.getRequestURI());
    //        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    //    }

    // Generic Exception (fallback)
    //Example: In a service
    //throw new IOException("File system is full");
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Something went wrong.", ex);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", request, null);
    }

    // Helper method
    private ResponseEntity<ErrorResponse> buildError(
            HttpStatus status,
            String message,
            HttpServletRequest request, List<String> errors) {
        String traceId = (String) request.getAttribute(RequestAttributes.TRACE_ID);
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                traceId
        );
        if (CollectionUtils.isNotEmpty(errors)) {
            errorResponse.setErrors(errors);
        }
        return new ResponseEntity<>(errorResponse, status);
    }
}
