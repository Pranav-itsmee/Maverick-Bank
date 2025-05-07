package com.example.mb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
	
	@Entity
	public class Transaction {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	
	    @ManyToOne
	    private Account fromAccount;
	
	    private Double amount;
	    private String transactionType; // UPI or BANK
	    private String status;
	    private String purpose;
	    private String description;
	    private String transactionMode;
	    private LocalDateTime transactionDate;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Account getFromAccount() {
			return fromAccount;
		}
		public void setFromAccount(Account fromAccount) {
			this.fromAccount = fromAccount;
		}
		public Double getAmount() {
			return amount;
		}
		public void setAmount(Double amount) {
			this.amount = amount;
		}
		public String getTransactionType() {
			return transactionType;
		}
		public void setTransactionType(String transactionType) {
			this.transactionType = transactionType;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getPurpose() {
			return purpose;
		}
		public void setPurpose(String purpose) {
			this.purpose = purpose;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getTransactionMode() {
			return transactionMode;
		}
		public void setTransactionMode(String transactionMode) {
			this.transactionMode = transactionMode;
		}
		public LocalDateTime getTransactionDate() {
			return transactionDate;
		}
		public void setTransactionDate(LocalDateTime transactionDate) {
			this.transactionDate = transactionDate;
		}
	
	    // Getters & Setters
	}
