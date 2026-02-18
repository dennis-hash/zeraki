package com.example.zeraki.controller;

import com.example.zeraki.dto.ApiResponse;
import com.example.zeraki.dto.CustomerRequest;
import com.example.zeraki.model.Customer;
import com.example.zeraki.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> create(@Valid @RequestBody CustomerRequest request) {
        Customer data = customerService.createCustomer(request);
        return new ResponseEntity<>(
                new ApiResponse<>(201, "Customer created successfully", data),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Customer>>> getAll() {
        List<Customer> data = customerService.getAllCustomers();
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Customers retrieved successfully", data)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> getById(@PathVariable Long id) {
        Customer data = customerService.getCustomerById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Customer found", data)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> update(@PathVariable Long id, @Valid @RequestBody CustomerRequest request) {
        Customer data = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Customer updated successfully", data)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Customer deleted successfully", null)
        );
    }
}