package com.example.zeraki.service;

import com.example.zeraki.dto.CustomerRequest;
import com.example.zeraki.model.Customer;
import com.example.zeraki.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;

    public Customer createCustomer(CustomerRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());
        customer.setCreatedAt(LocalDateTime.now()); // Manual set if Auditing is not enabled

        return repository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    public Customer updateCustomer(Long id, CustomerRequest request) {
        Customer existing = getCustomerById(id);

        // Check if new email belongs to someone else
        if (!existing.getEmail().equals(request.email()) && repository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        existing.setName(request.name());
        existing.setEmail(request.email());
        existing.setPhone(request.phone());

        return repository.save(existing);
    }

    public void deleteCustomer(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        repository.deleteById(id);
    }
}
