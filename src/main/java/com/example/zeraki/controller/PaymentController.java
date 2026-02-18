package com.example.zeraki.controller;

import com.example.zeraki.dto.ApiResponse;
import com.example.zeraki.dto.PaymentRequest;
import com.example.zeraki.model.Payment;
import com.example.zeraki.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<Payment>> create(@Valid @RequestBody PaymentRequest request) {
        Payment data = paymentService.processPayment(request);
        return new ResponseEntity<>(
                new ApiResponse<>(201, "Payment processed successfully", data),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Payment>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(200, "Payments retrieved", paymentService.getAllPayments()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Payment>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Payment found", paymentService.getPaymentById(id)));
    }
}
