package com.hex.maverickbank.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hex.maverickbank.exception.InvalidTransactionException;
import com.hex.maverickbank.exception.ResourceNotFoundException;
import com.hex.maverickbank.entity.Account;
import com.hex.maverickbank.entity.BankTransfer;
import com.hex.maverickbank.entity.Transaction;
import com.hex.maverickbank.entity.Upi;
import com.hex.maverickbank.repository.AccountRepository;
import com.hex.maverickbank.repository.BankTransferRepository;
import com.hex.maverickbank.repository.TransactionRepository;
import com.hex.maverickbank.repository.UpiRepository;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UpiRepository upiRepository;

    @Autowired
    private BankTransferRepository bankTransferRepository;

    @Autowired
    private AccountRepository accountRepository;

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
     * Save a UPI transaction with a corresponding Transaction, debiting the source account.
     * @param transaction Base transaction data
     * @param upi UPI-specific data
     * @return Saved UPI object
     */
    @Transactional
    public Upi addUpiTransaction(Transaction transaction, Upi upi) {
        Account account = debitSourceAccount(transaction);
        logger.info("Adding UPI transaction for accountId: {}", account.getId());

        transaction.setFromAccount(account);
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Link and save UPI details
        upi.setTransaction(savedTransaction);
        Upi savedUpi = upiRepository.save(upi);

        logger.info("UPI transaction saved with ID: {}", savedUpi.getId());
        return savedUpi;
    }

    /**
     * Save a Bank Transfer transaction with a corresponding Transaction, debiting the source
     * account and, if the beneficiary account number matches an internal account, crediting it.
     * @param transaction Base transaction data
     * @param bankTransfer Bank Transfer-specific data
     * @return Saved BankTransfer object
     */
    @Transactional
    public BankTransfer addBankTransferTransaction(Transaction transaction, BankTransfer bankTransfer) {
        Account account = debitSourceAccount(transaction);
        logger.info("Adding Bank Transfer transaction for accountId: {}", account.getId());

        accountRepository.findByAccountNumber(bankTransfer.getBeneficiaryAccountNumber())
                .ifPresent(destination -> {
                    destination.setBalance(destination.getBalance().add(BigDecimal.valueOf(transaction.getAmount())));
                    accountRepository.save(destination);
                    logger.info("Credited internal beneficiary account: {}", destination.getId());
                });

        transaction.setFromAccount(account);
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Link and save BankTransfer details
        bankTransfer.setTransaction(savedTransaction);
        BankTransfer savedTransfer = bankTransferRepository.save(bankTransfer);

        logger.info("Bank Transfer transaction saved with ID: {}", savedTransfer.getId());
        return savedTransfer;
    }

    /**
     * Validates and debits the account a transaction originates from: account must exist,
     * be ACTIVE, have sufficient balance, and stay within its daily/monthly transaction limit.
     * Also stamps the transaction with a server-side status and timestamp.
     */
    private Account debitSourceAccount(Transaction transaction) {
        if (transaction.getFromAccount() == null || transaction.getFromAccount().getId() == null) {
            throw new InvalidTransactionException("Transaction must reference a source account");
        }
        if (transaction.getAmount() == null || transaction.getAmount() <= 0) {
            throw new InvalidTransactionException("Transaction amount must be greater than zero");
        }

        Account account = accountRepository.findById(transaction.getFromAccount().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account not found with id: " + transaction.getFromAccount().getId()));

        if (!"ACTIVE".equalsIgnoreCase(account.getStatus())) {
            throw new InvalidTransactionException("Account is not active: " + account.getAccountNumber());
        }

        BigDecimal amount = BigDecimal.valueOf(transaction.getAmount());

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InvalidTransactionException("Insufficient balance in account: " + account.getAccountNumber());
        }

        if (account.getTransactionLimit() != null) {
            LocalDateTime dayStart = LocalDate.now().atStartOfDay();
            BigDecimal dailySoFar = BigDecimal.valueOf(transactionRepository
                    .sumSuccessfulAmountByAccountAndDateRange(account.getId(), dayStart, dayStart.plusDays(1)));
            if (dailySoFar.add(amount).compareTo(account.getTransactionLimit().getDailyLimit()) > 0) {
                throw new InvalidTransactionException("Transaction exceeds daily limit for account: " + account.getAccountNumber());
            }

            LocalDateTime monthStart = LocalDate.now().withDayOfMonth(1).atStartOfDay();
            BigDecimal monthlySoFar = BigDecimal.valueOf(transactionRepository
                    .sumSuccessfulAmountByAccountAndDateRange(account.getId(), monthStart, monthStart.plusMonths(1)));
            if (monthlySoFar.add(amount).compareTo(account.getTransactionLimit().getMonthlyLimit()) > 0) {
                throw new InvalidTransactionException("Transaction exceeds monthly limit for account: " + account.getAccountNumber());
            }
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        transaction.setStatus("SUCCESS");
        transaction.setTransactionDate(LocalDateTime.now());

        return account;
    }
}
