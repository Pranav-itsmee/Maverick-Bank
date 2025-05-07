package com.example.mb.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mb.model.Customer;
import com.example.mb.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    /**
     * Create a new Customer.
     * Logs the request and successful creation of the customer.
     */
    @PostMapping("/add")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        logger.info("POST /api/customers/add called with customer data: {}", customer);
        Customer createdCustomer = customerService.createCustomer(customer);
        logger.info("Customer created with ID: {}", createdCustomer.getId());
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    /**
     * Get all Customers.
     * Logs the request to fetch all customers.
     */
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        logger.info("GET /api/customers called");
        List<Customer> customers = customerService.getAllCustomers();
        logger.info("Fetched {} customers", customers.size());
        return ResponseEntity.ok(customers);
    }

    /**
     * Get a Customer by ID.
     * Logs the request and whether the customer is found or not.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) throws Exception {
        logger.info("GET /api/customers/{} called", id);
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            logger.info("Customer found with ID: {}", id);
            return ResponseEntity.ok(customer);
        } else {
            logger.warn("Customer not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}
