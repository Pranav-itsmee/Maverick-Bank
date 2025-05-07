package com.example.mb.service;

import com.example.mb.model.ServiceRequest;
import com.example.mb.repository.ServiceRequestRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServiceRequestServiceTest {

    @Mock
    private ServiceRequestRepository serviceRequestRepository;

    @InjectMocks
    private ServiceRequestService serviceRequestService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRequests() {
        ServiceRequest req1 = new ServiceRequest();
        ServiceRequest req2 = new ServiceRequest();
        when(serviceRequestRepository.findAll()).thenReturn(List.of(req1, req2));

        List<ServiceRequest> result = serviceRequestService.getAllRequests();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetRequestsByStatus() {
        ServiceRequest req = new ServiceRequest();
        when(serviceRequestRepository.findByStatus("PENDING")).thenReturn(List.of(req));

        List<ServiceRequest> result = serviceRequestService.getRequestsByStatus("PENDING");
        assertEquals(1, result.size());
    }

    @Test
    public void testGetRequestById_Found() {
        ServiceRequest req = new ServiceRequest();
        req.setId(1L);
        when(serviceRequestRepository.findById(1L)).thenReturn(Optional.of(req));

        ServiceRequest result = serviceRequestService.getRequestById(1L);
        assertNotNull(result);
    }


    @Test
    public void testUpdateServiceRequestStatus() {
        ServiceRequest existing = new ServiceRequest();
        existing.setId(1L);
        existing.setStatus("PENDING");

        when(serviceRequestRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(serviceRequestRepository.save(existing)).thenReturn(existing);

        ServiceRequest updated = serviceRequestService.updateServiceRequestStatus(1L, "RESOLVED");
        assertEquals("RESOLVED", updated.getStatus());
    }
}
