package com.example.mb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mb.model.ServiceRequest;
import com.example.mb.repository.ServiceRequestRepository;
import com.example.mb.exception.ResourceNotFoundException;

@Service
public class ServiceRequestService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceRequestService.class);

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    /**
     * Fetch all service requests.
     * @return List of all service requests.
     */
    public List<ServiceRequest> getAllRequests() {
        logger.info("Fetching all service requests");
        return serviceRequestRepository.findAll();
    }

    /**
     * Fetch service requests filtered by status.
     * @param status The status to filter by.
     * @return List of matching service requests.
     */
    public List<ServiceRequest> getRequestsByStatus(String status) {
        logger.info("Fetching service requests with status: {}", status);
        return serviceRequestRepository.findByStatus(status);
    }

    /**
     * Fetch service requests by customer ID.
     * @param customerId The ID of the customer.
     * @return List of service requests made by the customer.
     */
    public List<ServiceRequest> getRequestsByCustomerId(Long customerId) {
        logger.info("Fetching service requests for customerId: {}", customerId);
        return serviceRequestRepository.findByCustomerId(customerId);
    }

    /**
     * Fetch a single service request by its ID.
     * @param id The ID of the service request.
     * @return The service request.
     * @throws ResourceNotFoundException if not found.
     */
    public ServiceRequest getRequestById(Long id) {
        logger.info("Fetching service request with id: {}", id);
        return serviceRequestRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ServiceRequest not found with id: {}", id);
                    return new ResourceNotFoundException("Service Request not found with id: " + id);
                });
    }

    /**
     * Add a new service request.
     * @param serviceRequest The new service request.
     * @return The saved service request.
     */
    public ServiceRequest addServiceRequest(ServiceRequest serviceRequest) {
        logger.info("Adding new service request for customerId: {}", serviceRequest.getCustomer().getId());
        return serviceRequestRepository.save(serviceRequest);
    }

    /**
     * Update the status of a service request by ID.
     * @param id The ID of the service request.
     * @param status The new status.
     * @return Updated service request.
     * @throws ResourceNotFoundException if not found.
     */
    public ServiceRequest updateServiceRequestStatus(Long id, String status) {
        logger.info("Updating status of service request id: {} to status: {}", id, status);

        ServiceRequest existingRequest = serviceRequestRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ServiceRequest not found with id: {}", id);
                    return new ResourceNotFoundException("Service Request not found with id: " + id);
                });

        existingRequest.setStatus(status);
        ServiceRequest updatedRequest = serviceRequestRepository.save(existingRequest);

        logger.info("Successfully updated status of service request id: {}", id);
        return updatedRequest;
    }
}
