package com.example.zeraki.dto;

public record ApiResponse<T>(
        int status,
        String message,
        T data
) {
}
