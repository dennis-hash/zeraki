package com.example.zeraki.dto;

import java.time.LocalDate;

public record OverdueInvoiceResponse(
        Long invoiceId,
        String customerName,
        Double amount,
        Double paid,
        Double balance,
        LocalDate dueDate,
        Integer daysOverdue,
        String status
) {
}
