package com.example.zeraki.service;

import com.example.zeraki.Exceptions.BusinessRuleViolationException;
import com.example.zeraki.Exceptions.NotFoundException;
import com.example.zeraki.dto.PaymentRequest;
import com.example.zeraki.model.Invoice;
import com.example.zeraki.model.enums.InvoiceStatus;
import com.example.zeraki.model.Payment;
import com.example.zeraki.repository.InvoiceRepository;
import com.example.zeraki.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    @Transactional
    public Payment processPayment(PaymentRequest request) {

        //check if duplicate payment
        if (paymentRepository.existsByTransactionNumber(request.transactionNumber())) {
            throw new BusinessRuleViolationException( "Duplicate transaction: Transaction number " + request.transactionNumber() + " already exists.");
        }

        // invoice exists check
        Invoice invoice = invoiceRepository.findById(request.invoiceId()).orElseThrow(() -> new NotFoundException("Invoice not found"));

        //  total payments
        Double currentTotal = invoice.getPaid();

        // check for overpayment
        if (currentTotal + request.amount() > invoice.getAmount()) {
            throw new BusinessRuleViolationException("Payment exceeds invoice balance. Remaining: " + (invoice.getAmount() - currentTotal));
        }

        // save payment
        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setAmount(request.amount());
        payment.setPaymentMethod(request.paymentMethod());
        payment.setTransactionNumber(request.transactionNumber());
        payment.setCreatedAt(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        // update status
        updateInvoiceStatus(invoice, currentTotal + request.amount());

        return savedPayment;
    }

    private void updateInvoiceStatus(Invoice invoice, Double newTotal) {
        if (newTotal >= invoice.getAmount()) {
            invoice.setStatus(InvoiceStatus.PAID);
        } else {
            invoice.setStatus(InvoiceStatus.PARTIALLY_PAID);
        }
        invoiceRepository.save(invoice);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new NotFoundException("Payment record not found"));
    }
}
