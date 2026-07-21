package com.hex.maverickbank.dto;

import com.hex.maverickbank.entity.Transaction;
import com.hex.maverickbank.entity.Upi;

public class UpiTransactionRequest {
	 private Transaction transaction;
	    private Upi upi;

	    // Getters and Setters
	    public Transaction getTransaction() {
	        return transaction;
	    }

	    public void setTransaction(Transaction transaction) {
	        this.transaction = transaction;
	    }

	    public Upi getUpi() {
	        return upi;
	    }

	    public void setUpi(Upi upi) {
	        this.upi = upi;
	    }
}
