package com.example.mb.controller;

import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mb.model.ServiceQuery;
import com.example.mb.model.ServiceRequest;
import com.example.mb.repository.ServiceQueryRepository;
import com.example.mb.repository.ServiceRequestRepository;

@RestController
@RequestMapping("/api/service-queries")
@CrossOrigin(origins = "http://localhost:5173")
public class ServiceQueryController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceQueryController.class);

    

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ServiceQueryRepository serviceQueryRepository;

    /**
     * Create a new Service Query linked to an existing Service Request.
     * Logs the request and includes error handling for invalid data.
     */
    @PostMapping("/add")
    public ResponseEntity<?> createServiceQuery(@RequestBody Map<String, Object> requestData) {
        try {
            // Extracting data from request
            Long requestId = Long.valueOf(requestData.get("requestId").toString());
            String remarks = requestData.get("remarks").toString();
            String status = requestData.get("status").toString();
            LocalDate queryDate = LocalDate.parse(requestData.get("queryDate").toString().substring(0, 10)); // yyyy-MM-dd

            // Log incoming data
            logger.info("Received request to create service query for requestId: {}", requestId);

            // Fetch the corresponding Service Request
            ServiceRequest serviceRequest = serviceRequestRepository.findById(requestId)
                    .orElseThrow(() -> new RuntimeException("Service Request not found with id: " + requestId));

            // Creating the Service Query
            ServiceQuery serviceQuery = new ServiceQuery();
            serviceQuery.setServiceRequest(serviceRequest);
            serviceQuery.setRemarks(remarks);
            serviceQuery.setStatus(status);
            serviceQuery.setRespondedDate(queryDate);

            // Saving the Service Query
            ServiceQuery savedQuery = serviceQueryRepository.save(serviceQuery);

            // Log success
            logger.info("Service Query created successfully for requestId: {}", requestId);

            return new ResponseEntity<>(savedQuery, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error("Error while creating service query", e);
            return new ResponseEntity<>("Failed to create service query: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
