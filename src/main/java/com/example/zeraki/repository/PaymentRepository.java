package com.example.zeraki.repository;

import com.example.zeraki.dto.MonthlyRevenueResponse;
import com.example.zeraki.dto.TopCustomerResponse;
import com.example.zeraki.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByTransactionNumber(String transactionNumber);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.createdAt BETWEEN :start AND :end")
    Double sumPaymentsBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new com.example.zeraki.dto.TopCustomerResponse(p.invoice.customer.name, SUM(p.amount)) " +
            "FROM Payment p WHERE p.createdAt BETWEEN :start AND :end " +
            "GROUP BY p.invoice.customer.name " +
            "ORDER BY SUM(p.amount) DESC")
    List<TopCustomerResponse> findTopCustomers(LocalDateTime start, LocalDateTime end, org.springframework.data.domain.Pageable pageable);

    @Query("SELECT new com.example.zeraki.dto.MonthlyRevenueResponse(" +
            "CAST(FUNCTION('DATE_FORMAT', p.createdAt, '%Y-%m') AS String), " +
            "SUM(p.amount)) " +
            "FROM Payment p " +
            "WHERE p.createdAt BETWEEN :start AND :end " +
            "GROUP BY FUNCTION('DATE_FORMAT', p.createdAt, '%Y-%m')")
    List<MonthlyRevenueResponse> findMonthlyRevenue(LocalDateTime start, LocalDateTime end);


}
