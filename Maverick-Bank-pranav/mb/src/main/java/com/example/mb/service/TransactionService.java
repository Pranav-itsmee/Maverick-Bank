package com.example.mb.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mb.model.BankTransfer;
import com.example.mb.model.Transaction;
import com.example.mb.model.Upi;
import com.example.mb.repository.BankTransferRepository;
import com.example.mb.repository.TransactionRepository;
import com.example.mb.repository.UpiRepository;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UpiRepository upiRepository;

    @Autowired
    private BankTransferRepository bankTransferRepository;

    /**
     * Fetch all transactions.
     */
    public List<Transaction> getAllTransactions() {
        logger.info("Fetching all transactions");
        return transactionRepository.findAll();
    }

    /**
     * Fetch transaction by ID.
     * @param id Transaction ID
     * @return Optional<Transaction>
     */
    public Optional<Transaction> getTransactionById(Long id) {
        logger.info("Fetching transaction with id: {}", id);
        return transactionRepository.findById(id);
    }

    /**
     * Get all transactions by account ID (fromAccount).
     * @param accountId Account ID
     * @return List of Transactions
     */
    public List<Transaction> getTransactionsByAccount(Long accountId) {
        logger.info("Fetching transactions for accountId: {}", accountId);
        return transactionRepository.findByFromAccountId(accountId);
    }

    /**
     * Get all transactions by type (UPI, BANK_TRANSFER, etc.).
     * @param type Transaction type
     * @return List of Transactions
     */
    public List<Transaction> getTransactionsByType(String type) {
        logger.info("Fetching transactions with type: {}", type);
        return transactionRepository.findByTransactionTypeIgnoreCase(type);
    }

    /**
     * Save a UPI transaction with a corresponding Transaction.
     * @param transaction Base transaction data
     * @param upi UPI-specific data
     * @return Saved UPI object
     */
    public Upi addUpiTransaction(Transaction transaction, Upi upi) {
        logger.info("Adding UPI transaction for accountId: {}", transaction.getFromAccount().getId());

        // Save base transaction first
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Link and save UPI details
        upi.setTransaction(savedTransaction);
        Upi savedUpi = upiRepository.save(upi);

        logger.info("UPI transaction saved with ID: {}", savedUpi.getId());
        return savedUpi;
    }

    /**
     * Save a Bank Transfer transaction with a corresponding Transaction.
     * @param transaction Base transaction data
     * @param bankTransfer Bank Transfer-specific data
     * @return Saved BankTransfer object
     */
    public BankTransfer addBankTransferTransaction(Transaction transaction, BankTransfer bankTransfer) {
        logger.info("Adding Bank Transfer transaction for accountId: {}", transaction.getFromAccount().getId());

        // Save base transaction first
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Link and save BankTransfer details
        bankTransfer.setTransaction(savedTransaction);
        BankTransfer savedTransfer = bankTransferRepository.save(bankTransfer);

        logger.info("Bank Transfer transaction saved with ID: {}", savedTransfer.getId());
        return savedTransfer;
    }
}
