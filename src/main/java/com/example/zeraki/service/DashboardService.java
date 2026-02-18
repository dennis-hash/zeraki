package com.example.zeraki.service;

import com.example.zeraki.dto.DashBoardSummary;
import com.example.zeraki.dto.MonthlyRevenueResponse;
import com.example.zeraki.dto.TopCustomerResponse;
import com.example.zeraki.repository.CustomerRepository;
import com.example.zeraki.repository.InvoiceRepository;
import com.example.zeraki.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final CustomerRepository customerRepo;
    private final InvoiceRepository invoiceRepo;
    private final PaymentRepository paymentRepo;

    public DashBoardSummary getSummary(LocalDateTime start, LocalDateTime end) {
        long customers = customerRepo.countByCreatedAtBetween(start, end);
        long invoices = invoiceRepo.countByCreatedAtBetween(start, end);

        Double totalInvoiced = invoiceRepo.sumInvoicedAmountBetween(start, end);
        Double totalPaid = paymentRepo.sumPaymentsBetween(start, end);

        totalInvoiced = (totalInvoiced != null) ? totalInvoiced : 0.0;
        totalPaid = (totalPaid != null) ? totalPaid : 0.0;

        return new DashBoardSummary(
                customers,
                invoices,
                totalInvoiced,
                totalPaid,
                totalInvoiced - totalPaid
        );
    }

    public List<TopCustomerResponse> getTopCustomers(LocalDateTime start, LocalDateTime end) {
        return paymentRepo.findTopCustomers(start, end, PageRequest.of(0, 5));
    }

    public List<MonthlyRevenueResponse> getMonthlyRevenue(LocalDateTime start, LocalDateTime end) {
        return paymentRepo.findMonthlyRevenue(start, end);
    }
}
