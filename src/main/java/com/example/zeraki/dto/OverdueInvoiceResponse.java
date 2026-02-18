package com.example.zeraki.dto;

import java.time.LocalDate;

public record OverdueInvoiceResponse(
        Long invoiceId,
        String customerName,
        Double amount,
        Double amountPaid,
        Double balance,
        LocalDate dueDate,
        long daysOverdue,
        String status
) {
}
