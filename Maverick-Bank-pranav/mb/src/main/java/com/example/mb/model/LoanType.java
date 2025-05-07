package com.example.mb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "loan_type")
public class LoanType {

    public enum LoanTypeName {
        PERSONAL,
        HOME,
        VEHICLE,
        EDUCATION,
        BUSINESS,
        GOLD
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LoanTypeName name;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LoanTypeName getName() {
        return name;
    }

    public void setName(LoanTypeName name) {
        this.name = name;
    }
}
