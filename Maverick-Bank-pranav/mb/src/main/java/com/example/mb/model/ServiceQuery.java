package com.example.mb.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "service_query")
public class ServiceQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private ServiceRequest serviceRequest;


    @Column(nullable = false)
    private String remarks;

    @Column(nullable = false)
    private String status;

    @Column
    private LocalDate respondedDate;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getRespondedDate() {
        return respondedDate;
    }

    public void setRespondedDate(LocalDate respondedDate) {
        this.respondedDate = respondedDate;
    }
}
