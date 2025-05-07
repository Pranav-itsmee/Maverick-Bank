package com.example.mb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "loan_eligibility")
public class LoanEligibility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private LoanType loanType;

    @Column(nullable = false)
    private int minAge;

    @Column(nullable = false)
    private int minCibilScore;

    @Column(nullable = false)
    private double minIncome;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMinCibilScore() {
        return minCibilScore;
    }

    public void setMinCibilScore(int minCibilScore) {
        this.minCibilScore = minCibilScore;
    }

    public double getMinIncome() {
        return minIncome;
    }

    public void setMinIncome(double minIncome) {
        this.minIncome = minIncome;
    }
}
