package com.example.mb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "gold_loan")
public class GoldLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bankStatementsUrl;

    @Column(nullable = false)
    private int cibilScore;

    @Column(nullable = false)
    private String existingLoans;

    @Column(nullable = false)
    private String goldPurity;

    @Column(nullable = false)
    private String goldType;

    @Column(nullable = false)
    private double goldWeight;

    @Column(nullable = false)
    private String itrUrl;

    @Column
    private String remarks;

    @Column(nullable = false)
    private String salarySlipsUrl;

    @Column(nullable = false)
    private String valuationReportUrl;

    @ManyToOne
    private LoanApplication loanApplication;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankStatementsUrl() {
        return bankStatementsUrl;
    }

    public void setBankStatementsUrl(String bankStatementsUrl) {
        this.bankStatementsUrl = bankStatementsUrl;
    }

    public int getCibilScore() {
        return cibilScore;
    }

    public void setCibilScore(int cibilScore) {
        this.cibilScore = cibilScore;
    }

    public String getExistingLoans() {
        return existingLoans;
    }

    public void setExistingLoans(String existingLoans) {
        this.existingLoans = existingLoans;
    }

    public String getGoldPurity() {
        return goldPurity;
    }

    public void setGoldPurity(String goldPurity) {
        this.goldPurity = goldPurity;
    }

    public String getGoldType() {
        return goldType;
    }

    public void setGoldType(String goldType) {
        this.goldType = goldType;
    }

    public double getGoldWeight() {
        return goldWeight;
    }

    public void setGoldWeight(double goldWeight) {
        this.goldWeight = goldWeight;
    }

    public String getItrUrl() {
        return itrUrl;
    }

    public void setItrUrl(String itrUrl) {
        this.itrUrl = itrUrl;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSalarySlipsUrl() {
        return salarySlipsUrl;
    }

    public void setSalarySlipsUrl(String salarySlipsUrl) {
        this.salarySlipsUrl = salarySlipsUrl;
    }

    public String getValuationReportUrl() {
        return valuationReportUrl;
    }

    public void setValuationReportUrl(String valuationReportUrl) {
        this.valuationReportUrl = valuationReportUrl;
    }

    public LoanApplication getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
    }
}
