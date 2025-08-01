package com.example.paging.entity;

public class ApiResponse<T> {

    private final T content;

    public ApiResponse(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public static <T> ApiResponse<T> of(T content) {
        return new ApiResponse<T>(content);
    }
}