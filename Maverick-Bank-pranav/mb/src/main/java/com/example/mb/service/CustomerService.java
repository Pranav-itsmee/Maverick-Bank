package com.example.mb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.mb.model.Customer;
import com.example.mb.repository.CustomerRepository;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class); // Logger initialization

    @Autowired
    private CustomerRepository customerRepository;

    // Create a new customer
    public Customer createCustomer(Customer customer) {
        logger.info("Creating a new customer with name: {}", customer.getName());
        return customerRepository.save(customer);
    }

    // Get a list of all customers
    public List<Customer> getAllCustomers() {
        logger.info("Fetching all customers.");
        return customerRepository.findAll();
    }

    // Get a customer by their ID
    public Customer getCustomerById(Long id) throws Exception {
        logger.info("Fetching customer with ID: {}", id);
        
        return customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Customer not found with ID: {}", id); // Log error if customer is not found
                    return new Exception("Customer not found with id: " + id);
                });
    }
}
