package com.example.mb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "education_loan")
public class EducationLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String admissionLetterUrl;

    @Column(nullable = false)
    private int cibilScore;

    @Column(nullable = false)
    private String coApplicantBankStatementsUrl;

    @Column(nullable = false)
    private String coApplicantITRUrl;

    @Column(nullable = false)
    private String examScorecardUrl;

    @Column(nullable = false)
    private String existingLoans;

    @Column(nullable = false)
    private String feeStructureUrl;

    @Column(nullable = false)
    private String incomeType;

    @Column(nullable = false)
    private String insuranceDocsUrl;

    @Column(nullable = false)
    private String markSheetsUrl;

    @Column(nullable = false)
    private String propertyDocsUrl;

    @Column(nullable = false)
    private String salarySlipsUrl;

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

    public String getAdmissionLetterUrl() {
        return admissionLetterUrl;
    }

    public void setAdmissionLetterUrl(String admissionLetterUrl) {
        this.admissionLetterUrl = admissionLetterUrl;
    }

    public int getCibilScore() {
        return cibilScore;
    }

    public void setCibilScore(int cibilScore) {
        this.cibilScore = cibilScore;
    }

    public String getCoApplicantBankStatementsUrl() {
        return coApplicantBankStatementsUrl;
    }

    public void setCoApplicantBankStatementsUrl(String coApplicantBankStatementsUrl) {
        this.coApplicantBankStatementsUrl = coApplicantBankStatementsUrl;
    }

    public String getCoApplicantITRUrl() {
        return coApplicantITRUrl;
    }

    public void setCoApplicantITRUrl(String coApplicantITRUrl) {
        this.coApplicantITRUrl = coApplicantITRUrl;
    }

    public String getExamScorecardUrl() {
        return examScorecardUrl;
    }

    public void setExamScorecardUrl(String examScorecardUrl) {
        this.examScorecardUrl = examScorecardUrl;
    }

    public String getExistingLoans() {
        return existingLoans;
    }

    public void setExistingLoans(String existingLoans) {
        this.existingLoans = existingLoans;
    }

    public String getFeeStructureUrl() {
        return feeStructureUrl;
    }

    public void setFeeStructureUrl(String feeStructureUrl) {
        this.feeStructureUrl = feeStructureUrl;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public String getInsuranceDocsUrl() {
        return insuranceDocsUrl;
    }

    public void setInsuranceDocsUrl(String insuranceDocsUrl) {
        this.insuranceDocsUrl = insuranceDocsUrl;
    }

    public String getMarkSheetsUrl() {
        return markSheetsUrl;
    }

    public void setMarkSheetsUrl(String markSheetsUrl) {
        this.markSheetsUrl = markSheetsUrl;
    }

    public String getPropertyDocsUrl() {
        return propertyDocsUrl;
    }

    public void setPropertyDocsUrl(String propertyDocsUrl) {
        this.propertyDocsUrl = propertyDocsUrl;
    }

    public String getSalarySlipsUrl() {
        return salarySlipsUrl;
    }

    public void setSalarySlipsUrl(String salarySlipsUrl) {
        this.salarySlipsUrl = salarySlipsUrl;
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
