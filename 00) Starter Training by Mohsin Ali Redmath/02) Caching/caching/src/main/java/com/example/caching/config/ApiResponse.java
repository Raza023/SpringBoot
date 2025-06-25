package com.example.caching.config;

public class ApiResponse {
    private String message;

    public ApiResponse(String message) {
        this.message = message;
    }

    // Getter & Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
