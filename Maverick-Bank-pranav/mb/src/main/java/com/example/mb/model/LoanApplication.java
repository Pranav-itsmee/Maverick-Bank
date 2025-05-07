package com.example.mb.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loan_application")
public class LoanApplication {

    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }

    public enum Disbursed {
        YES,
        NO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private Disbursed disbursed;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private LoanType loanType;

    @Column(nullable = false)
    private LocalDate applicationDate;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Disbursed getDisbursed() {
        return disbursed;
    }

    public void setDisbursed(Disbursed disbursed) {
        this.disbursed = disbursed;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }
}
