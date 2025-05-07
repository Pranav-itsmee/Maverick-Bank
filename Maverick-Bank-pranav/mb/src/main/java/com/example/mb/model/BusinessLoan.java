package com.example.mb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "business_loan")
public class BusinessLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bankStatementsUrl;

    @Column(nullable = false)
    private String businessName;

    @Column(nullable = false)
    private String businessPanCardUrl;

    @Column(nullable = false)
    private String certificateOfIncorporationUrl;

    @Column(nullable = false)
    private int cibilScore;

    @Column(nullable = false)
    private String existingLoans;

    @Column(nullable = false)
    private String gstRegistrationUrl;

    @Column(nullable = false)
    private String itrBalanceSheetUrl;

    @Column(nullable = false)
    private String profitLossStatementUrl;

    @Column
    private String remarks;

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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessPanCardUrl() {
        return businessPanCardUrl;
    }

    public void setBusinessPanCardUrl(String businessPanCardUrl) {
        this.businessPanCardUrl = businessPanCardUrl;
    }

    public String getCertificateOfIncorporationUrl() {
        return certificateOfIncorporationUrl;
    }

    public void setCertificateOfIncorporationUrl(String certificateOfIncorporationUrl) {
        this.certificateOfIncorporationUrl = certificateOfIncorporationUrl;
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

    public String getGstRegistrationUrl() {
        return gstRegistrationUrl;
    }

    public void setGstRegistrationUrl(String gstRegistrationUrl) {
        this.gstRegistrationUrl = gstRegistrationUrl;
    }

    public String getItrBalanceSheetUrl() {
        return itrBalanceSheetUrl;
    }

    public void setItrBalanceSheetUrl(String itrBalanceSheetUrl) {
        this.itrBalanceSheetUrl = itrBalanceSheetUrl;
    }

    public String getProfitLossStatementUrl() {
        return profitLossStatementUrl;
    }

    public void setProfitLossStatementUrl(String profitLossStatementUrl) {
        this.profitLossStatementUrl = profitLossStatementUrl;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LoanApplication getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
    }
}
