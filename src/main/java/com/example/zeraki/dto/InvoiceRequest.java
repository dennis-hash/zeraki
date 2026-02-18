package com.example.zeraki.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record InvoiceRequest(
        @NotNull(message = "ID is required")
        Long customerId,

        @Positive(message = "Amount should be positive")
        Double amount,

        @Future(message = "Due date must be in the future")
        LocalDateTime dueDate
) {
}
