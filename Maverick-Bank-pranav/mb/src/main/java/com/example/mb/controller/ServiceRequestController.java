package com.example.mb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.mb.model.ServiceRequest;
import com.example.mb.service.ServiceRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/service-requests")
@CrossOrigin(origins = "http://localhost:5173")
public class ServiceRequestController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceRequestController.class); // Logger initialization

    @Autowired
    private ServiceRequestService serviceRequestService;

    // Get all service requests
    @GetMapping("/getAll")
    public ResponseEntity<List<ServiceRequest>> getAllRequests() {
        logger.info("Fetching all service requests");
        List<ServiceRequest> serviceRequests = serviceRequestService.getAllRequests();
        if (serviceRequests.isEmpty()) {
            logger.warn("No service requests found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No content available
        }
        logger.info("Successfully fetched {} service requests", serviceRequests.size());
        return ResponseEntity.ok(serviceRequests); // Returning the list of service requests
    }

    // Get service requests by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ServiceRequest>> getRequestsByStatus(@PathVariable String status) {
        logger.info("Fetching service requests with status: {}", status);
        List<ServiceRequest> serviceRequests = serviceRequestService.getRequestsByStatus(status);
        if (serviceRequests.isEmpty()) {
            logger.warn("No service requests found with status: {}", status);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No content available
        }
        logger.info("Successfully fetched {} service requests with status: {}", serviceRequests.size(), status);
        return ResponseEntity.ok(serviceRequests); // Returning the list of service requests by status
    }

    // Get service requests by customer ID
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ServiceRequest>> getRequestsByCustomerId(@PathVariable Long customerId) {
        logger.info("Fetching service requests for customer ID: {}", customerId);
        List<ServiceRequest> serviceRequests = serviceRequestService.getRequestsByCustomerId(customerId);
        if (serviceRequests.isEmpty()) {
            logger.warn("No service requests found for customer ID: {}", customerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No content available
        }
        logger.info("Successfully fetched {} service requests for customer ID: {}", serviceRequests.size(), customerId);
        return ResponseEntity.ok(serviceRequests); // Returning the list of service requests for a specific customer
    }

    // Get service request by ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequest> getRequestById(@PathVariable("id") Long id) {
        logger.info("Fetching service request with ID: {}", id);
        ServiceRequest serviceRequest = serviceRequestService.getRequestById(id);
        if (serviceRequest == null) {
            logger.error("Service request not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Service request not found
        }
        logger.info("Successfully fetched service request with ID: {}", id);
        return ResponseEntity.ok(serviceRequest); // Returning the service request
    }

    // Add a new service request
    @PostMapping("/add")
    public ResponseEntity<ServiceRequest> createServiceRequest(@RequestBody ServiceRequest serviceRequest) {
        logger.info("Creating new service request");
        ServiceRequest createdServiceRequest = serviceRequestService.addServiceRequest(serviceRequest);
        logger.info("Successfully created service request with ID: {}", createdServiceRequest.getId());
        return new ResponseEntity<>(createdServiceRequest, HttpStatus.CREATED); // Returning the created service request
    }

    // Update service request status
    @PutMapping("/{id}")
    public ResponseEntity<ServiceRequest> updateServiceRequestStatus(@PathVariable("id") Long id, @RequestBody Map<String, String> body) {
        logger.info("Updating service request status for ID: {}", id);
        String status = body.get("status");
        ServiceRequest updatedRequest = serviceRequestService.updateServiceRequestStatus(id, status);
        if (updatedRequest == null) {
            logger.error("Service request not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Service request not found
        }
        logger.info("Successfully updated service request status for ID: {}", id);
        return ResponseEntity.ok(updatedRequest); // Returning the updated service request
    }
}
