package com.example.zeraki.controller;

import com.example.zeraki.dto.ApiResponse;
import com.example.zeraki.dto.InvoiceRequest;
import com.example.zeraki.dto.OverdueInvoiceResponse;
import com.example.zeraki.model.Invoice;
import com.example.zeraki.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<ApiResponse<Invoice>> create(@Valid @RequestBody InvoiceRequest request) {
        Invoice data = invoiceService.createInvoice(request);
        return new ResponseEntity<>(
                new ApiResponse<>(201, "Invoice created successfully", data),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Invoice>>> getAll() {
        List<Invoice> data = invoiceService.getAllInvoices();
        return ResponseEntity.ok(new ApiResponse<>(200, "Invoices retrieved successfully", data));
    }

    @GetMapping("/overdue")
    public ResponseEntity<ApiResponse<List<OverdueInvoiceResponse>>> getOverdue(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        LocalDateTime start = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime end = (endDate != null) ? endDate.atTime(23, 59, 59) : null;

        List<OverdueInvoiceResponse> data = invoiceService.getOverdueInvoices(customerId, start, end);
        return ResponseEntity.ok(new ApiResponse<>(200, "Overdue invoices retrieved", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Invoice>> getById(@PathVariable Long id) {
        Invoice data = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Invoice found", data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Invoice deleted successfully", null));
    }
}
