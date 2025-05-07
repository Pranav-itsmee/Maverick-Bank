package com.example.mb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "personal_loan")
public class PersonalLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int cibilScore;

    @Column(nullable = false)
    private boolean existingLoans; // true for Yes, false for No

    @Column(nullable = false)
    private String bankStatementsUrl;

    @Column(nullable = false)
    private String employmentLetterUrl;

    @Column(nullable = false)
    private String form16Url;

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

    public int getCibilScore() {
        return cibilScore;
    }

    public void setCibilScore(int cibilScore) {
        this.cibilScore = cibilScore;
    }

    public boolean isExistingLoans() {
        return existingLoans;
    }

    public void setExistingLoans(boolean existingLoans) {
        this.existingLoans = existingLoans;
    }

    public String getBankStatementsUrl() {
        return bankStatementsUrl;
    }

    public void setBankStatementsUrl(String bankStatementsUrl) {
        this.bankStatementsUrl = bankStatementsUrl;
    }

    public String getEmploymentLetterUrl() {
        return employmentLetterUrl;
    }

    public void setEmploymentLetterUrl(String employmentLetterUrl) {
        this.employmentLetterUrl = employmentLetterUrl;
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
