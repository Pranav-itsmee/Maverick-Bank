package com.example.mb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "department")
public class Department {

    public enum Role {
        CSR,           // Customer Service Representative
        DLM,           // Document Loan Manager
        BRANCH_MANAGER,
        REGIONAL_MANAGER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Store the enum as a string in the database using EnumType.STRING
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
