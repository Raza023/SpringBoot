package com.example.orderclientservice.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleClientError(HttpClientErrorException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body("Client Error: " + ex.getMessage());
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<String> handleConnectionError(ResourceAccessException ex) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Product Service Down");
    }

    //REST Client Exceptions
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> handleServerError(HttpServerErrorException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body("Remote Server Error: " + ex.getMessage());
    }

    //If you want one generic handler instead of separate 4xx & 5xx:
    //If you add this, you don’t need separate HttpClientErrorException / HttpServerErrorException.
    //@ExceptionHandler(RestClientResponseException.class)
    //    public ResponseEntity<String> handleRestClientResponse(RestClientResponseException ex) {
    //        return ResponseEntity
    //                .status(ex.getStatusCode())
    //                .body("REST Error: " + ex.getResponseBodyAsString());
    //    }

    //If you use @Valid in controllers:
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationError(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    //JSON Parsing Errors
    //Occurs when JSON is malformed:
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidJson(
            HttpMessageNotReadableException ex) {

        return ResponseEntity
                .badRequest()
                .body("Invalid JSON format");
    }

    //Timeout & Connection Specific Handling
    //You are catching ResourceAccessException, but you can inspect root cause:
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<String> handleConnectionSpecificError(ResourceAccessException ex) {

        Throwable root = ex.getRootCause();

        if (root instanceof SocketTimeoutException) {
            return ResponseEntity
                    .status(HttpStatus.GATEWAY_TIMEOUT)
                    .body("Product Service Timeout");
        }

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Product Service Down");
    }

    //Catch-All Handler (MUST HAVE)
    //Without this, unexpected exceptions return default white-label error.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }


}

