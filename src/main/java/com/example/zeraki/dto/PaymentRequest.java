package com.example.zeraki.dto;

import com.example.zeraki.model.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PaymentRequest(
        @NotNull(message = "Invoice ID is required")
        Long invoiceId,

        @Positive(message = "Payment amount must be positive")
        Double amount,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @NotBlank(message = "Transaction number is required")
        String transactionNumber
) {}
