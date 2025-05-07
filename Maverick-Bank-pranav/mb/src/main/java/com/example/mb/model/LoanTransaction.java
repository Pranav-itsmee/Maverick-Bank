package com.example.mb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_transaction")
public class LoanTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the general transaction table
    @OneToOne
    private Transaction transaction;

    // Link to the associated loan application
    @ManyToOne
    private LoanApplication loanApplication;

    // Link to specific EMI / scheduled loan payment
    @ManyToOne
    private LoanPayment loanPayment;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public LoanApplication getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
    }

    public LoanPayment getLoanPayment() {
        return loanPayment;
    }

    public void setLoanPayment(LoanPayment loanPayment) {
        this.loanPayment = loanPayment;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}
