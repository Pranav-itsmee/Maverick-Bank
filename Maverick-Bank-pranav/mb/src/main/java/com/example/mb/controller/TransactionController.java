package com.example.mb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.mb.model.BankTransfer;
import com.example.mb.model.BankTransferTransactionRequest;
import com.example.mb.model.Upi;
import com.example.mb.model.UpiTransactionRequest;
import com.example.mb.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class); // Logger initialization

    @Autowired
    private TransactionService transactionService;

    // Get all transactions
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllTransactions() {
        logger.info("Fetching all transactions");
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    // Get transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable("id") Long id) {
        logger.info("Fetching transaction with ID: {}", id);
        return transactionService.getTransactionById(id)
                .map(transaction -> {
                    logger.info("Successfully fetched transaction with ID: {}", id);
                    return ResponseEntity.ok(transaction);
                })
                .orElseGet(() -> {
                    logger.error("Transaction not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Get transactions by account ID
    @GetMapping("/account/{accountId}")
    public ResponseEntity<?> getTransactionsByAccount(@PathVariable("accountId") Long accountId) {
        logger.info("Fetching transactions for account ID: {}", accountId);
        return ResponseEntity.ok(transactionService.getTransactionsByAccount(accountId));
    }

    // Get transactions by type (e.g., 'DEPOSIT', 'WITHDRAWAL')
    @GetMapping("/type/{type}")
    public ResponseEntity<?> getTransactionsByType(@PathVariable String type) {
        logger.info("Fetching transactions of type: {}", type);
        return ResponseEntity.ok(transactionService.getTransactionsByType(type));
    }

    // Add UPI transaction
    @PostMapping("/add-upi")
    public Upi addUpiTransaction(@RequestBody UpiTransactionRequest request) {
        logger.info("Adding new UPI transaction");
        Upi upiTransaction = transactionService.addUpiTransaction(request.getTransaction(), request.getUpi());
        logger.info("Successfully added UPI transaction with ID: {}", upiTransaction.getId());
        return upiTransaction;
    }

    // Add bank transfer transaction
    @PostMapping("/add-bank-transfer")
    public BankTransfer addBankTransferTransaction(@RequestBody BankTransferTransactionRequest request) {
        logger.info("Adding new bank transfer transaction");
        BankTransfer bankTransferTransaction = transactionService.addBankTransferTransaction(request.getTransaction(), request.getBankTransfer());
        logger.info("Successfully added bank transfer transaction with ID: {}", bankTransferTransaction.getId());
        return bankTransferTransaction;
    }
}
