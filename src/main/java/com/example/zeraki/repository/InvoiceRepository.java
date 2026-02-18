package com.example.zeraki.repository;

import com.example.zeraki.dto.OverdueInvoiceResponse;
import com.example.zeraki.model.Invoice;
import com.example.zeraki.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    @Query("SELECT SUM(i.amount) FROM Invoice i WHERE i.createdAt BETWEEN :start AND :end")
    Double sumInvoicedAmountBetween(LocalDateTime start, LocalDateTime end);

    // In InvoiceRepository.java

    @Query("SELECT new com.example.zeraki.dto.OverdueInvoiceResponse(" +
            "i.id, " +
            "i.customer.name, " +
            "i.amount, " +
            "i.paid, " +
            "(i.amount - i.paid), " +
            "CAST(i.dueDate AS localdate), " +
            "FUNCTION('DATEDIFF', :now, i.dueDate), " +
            "'OVERDUE') " +
            "FROM Invoice i " +
            "WHERE i.status != 'PAID' AND i.dueDate < :now " +
            "AND (:customerId IS NULL OR i.customer.id = :customerId) " +
            "AND (:start IS NULL OR i.createdAt >= :start) " +
            "AND (:end IS NULL OR i.createdAt <= :end)")
    List<OverdueInvoiceResponse> findOverdueInvoicesCalculated(
            LocalDateTime now,
            Long customerId,
            LocalDateTime start,
            LocalDateTime end
    );
}
