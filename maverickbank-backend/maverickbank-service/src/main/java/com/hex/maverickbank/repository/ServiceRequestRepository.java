package com.hex.maverickbank.repository;

import com.hex.maverickbank.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByStatus(String status);
    List<ServiceRequest> findByCustomerId(Long customerId);
}
