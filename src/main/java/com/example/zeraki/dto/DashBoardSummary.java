package com.example.zeraki.dto;

public record DashBoardSummary (
        long totalCustomers,
        long totalInvoices,
        Double totalAmountInvoiced,
        Double totalAmountPaid,
        Double outstandingBalance
) {

}
