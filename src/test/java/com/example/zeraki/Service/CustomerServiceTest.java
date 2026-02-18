package com.example.zeraki.Service;

import com.example.zeraki.dto.CustomerRequest;
import com.example.zeraki.model.Customer;
import com.example.zeraki.repository.CustomerRepository;
import com.example.zeraki.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private CustomerRequest validRequest;
    private Customer mockCustomer;

    @BeforeEach
    void setUp() {
        validRequest = new CustomerRequest("Dennis Kariuki", "dennis@gmail.com", "01127254244");

        mockCustomer = new Customer();
        mockCustomer.setId(1L);
        mockCustomer.setName("Dennis Kariuki");
        mockCustomer.setEmail("dennis@gmail.com");
        mockCustomer.setPhone("01127254244");
    }

    @Test
    @DisplayName("Should successfully create a customer when email is unique")
    void testCreateCustomerSuccess() {
        // Arrange
        when(customerRepository.existsByEmail(validRequest.email())).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(mockCustomer);

        // Act
        Customer savedCustomer = customerService.createCustomer(validRequest);

        // Assert
        assertNotNull(savedCustomer);
        assertEquals("dennis@gmail.com", savedCustomer.getEmail());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should throw 400 Bad Request when creating customer with duplicate email")
    void testCreateCustomerDuplicateEmail() {
        // Arrange
        when(customerRepository.existsByEmail(validRequest.email())).thenReturn(true);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            customerService.createCustomer(validRequest);
        });

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("Email already exists", exception.getReason());
        verify(customerRepository, never()).save(any(Customer.class));
    }




}