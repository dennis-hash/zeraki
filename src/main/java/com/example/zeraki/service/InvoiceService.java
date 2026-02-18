package com.example.zeraki.service;

import com.example.zeraki.Exceptions.NotFoundException;
import com.example.zeraki.dto.InvoiceRequest;
import com.example.zeraki.dto.OverdueInvoiceResponse;
import com.example.zeraki.model.Customer;
import com.example.zeraki.model.Invoice;
import com.example.zeraki.model.enums.InvoiceStatus;
import com.example.zeraki.repository.CustomerRepository;
import com.example.zeraki.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;

    public Invoice createInvoice(InvoiceRequest request) {
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer ID"));

        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setAmount(request.amount());
        invoice.setDueDate(request.dueDate());
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setCreatedAt(LocalDateTime.now());

        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice not found"));
    }


    // update invoice
    public void updateInvoiceStatus(Long invoiceId, Double totalPayments) {
        Invoice invoice = getInvoiceById(invoiceId);
        double totalPaid = invoice.getPaid() + totalPayments;

        if (totalPaid >= invoice.getAmount()) {
            invoice.setStatus(InvoiceStatus.PAID);
        } else if (totalPaid < invoice.getAmount() && invoice.getAmount() > 0 ) {
            invoice.setStatus(InvoiceStatus.PARTIALLY_PAID);
        } else {
            invoice.setStatus(LocalDateTime.now().isAfter(invoice.getDueDate())
                    ? InvoiceStatus.OVERDUE : InvoiceStatus.PENDING);
        }
        invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Long id) {
        Invoice invoice = getInvoiceById(id);

        if (invoice.getPaid() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete invoice with existing payments");
        }

        invoiceRepository.delete(invoice);
    }

    public List<OverdueInvoiceResponse> getOverdueInvoices(Long customerId, LocalDateTime start, LocalDateTime end) {
        return invoiceRepository.findOverdueInvoicesCalculated(
                LocalDateTime.now(),
                customerId,
                start,
                end
        );
    }
}
