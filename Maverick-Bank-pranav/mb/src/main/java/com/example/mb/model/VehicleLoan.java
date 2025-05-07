package com.example.mb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicle_loan")
public class VehicleLoan {

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
    private String proformaInvoiceUrl;

    @Column(nullable = false)
    private String relationship;

    @Column(nullable = false)
    private String vehicleType;

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

    public String getProformaInvoiceUrl() {
        return proformaInvoiceUrl;
    }

    public void setProformaInvoiceUrl(String proformaInvoiceUrl) {
        this.proformaInvoiceUrl = proformaInvoiceUrl;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
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
