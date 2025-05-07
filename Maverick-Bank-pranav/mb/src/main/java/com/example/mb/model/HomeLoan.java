package com.example.mb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "home_loan")
public class HomeLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int cibilScore;

    @Column(nullable = false)
    private String coApplicantName;

    @Column(nullable = false)
    private String coApplicantPan;

    @Column(nullable = false)
    private String existingLoans;

    @Column(nullable = false)
    private String approvedPlanUrl;

    @Column(nullable = false)
    private String bankStatementsUrl;

    @Column(nullable = false)
    private String form16Url;

    @Column(nullable = false)
    private String salarySlipsUrl;

    @Column(nullable = false)
    private String saleAgreementUrl;

    @Column(nullable = false)
    private String titleDeedUrl;

    @Column(nullable = false)
    private String relationship;

    @Column
    private String remarks;

    @ManyToOne(optional = false)
    private LoanApplication loanApplication;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCibilScore() {
        return cibilScore;
    }

    public void setCibilScore(int cibilScore) {
        this.cibilScore = cibilScore;
    }

    public String getCoApplicantName() {
        return coApplicantName;
    }

    public void setCoApplicantName(String coApplicantName) {
        this.coApplicantName = coApplicantName;
    }

    public String getCoApplicantPan() {
        return coApplicantPan;
    }

    public void setCoApplicantPan(String coApplicantPan) {
        this.coApplicantPan = coApplicantPan;
    }

    public String getExistingLoans() {
        return existingLoans;
    }

    public void setExistingLoans(String existingLoans) {
        this.existingLoans = existingLoans;
    }

    public String getApprovedPlanUrl() {
        return approvedPlanUrl;
    }

    public void setApprovedPlanUrl(String approvedPlanUrl) {
        this.approvedPlanUrl = approvedPlanUrl;
    }

    public String getBankStatementsUrl() {
        return bankStatementsUrl;
    }

    public void setBankStatementsUrl(String bankStatementsUrl) {
        this.bankStatementsUrl = bankStatementsUrl;
    }

    public String getForm16Url() {
        return form16Url;
    }

    public void setForm16Url(String form16Url) {
        this.form16Url = form16Url;
    }

    public String getSalarySlipsUrl() {
        return salarySlipsUrl;
    }

    public void setSalarySlipsUrl(String salarySlipsUrl) {
        this.salarySlipsUrl = salarySlipsUrl;
    }

    public String getSaleAgreementUrl() {
        return saleAgreementUrl;
    }

    public void setSaleAgreementUrl(String saleAgreementUrl) {
        this.saleAgreementUrl = saleAgreementUrl;
    }

    public String getTitleDeedUrl() {
        return titleDeedUrl;
    }

    public void setTitleDeedUrl(String titleDeedUrl) {
        this.titleDeedUrl = titleDeedUrl;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
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
