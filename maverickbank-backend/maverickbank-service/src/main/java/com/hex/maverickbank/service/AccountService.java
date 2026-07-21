package com.hex.maverickbank.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hex.maverickbank.exception.ResourceNotFoundException;
import com.hex.maverickbank.entity.Account;
import com.hex.maverickbank.entity.TransactionLimit;
import com.hex.maverickbank.repository.AccountRepository;
import com.hex.maverickbank.repository.TransactionLimitRepository;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class); // Logger initialization

    private static final Set<String> VALID_STATUSES = Set.of("ACTIVE", "DEACTIVE");

    private final AccountRepository accountRepository;

    @Autowired
    private TransactionLimitRepository transactionLimitRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Get all accounts
    public List<Account> getAllAccounts() {
        logger.info("Fetching all accounts");
        return accountRepository.findAll();
    }

    // Get account by ID
    public Optional<Account> getAccountById(Long id) {
        logger.info("Fetching account with ID: {}", id);
        return accountRepository.findById(id);
    }

    // Get accounts by customer name (case-insensitive)
    public List<Account> getAccountsByCustomerName(String name) {
        logger.info("Fetching accounts for customer with name: {}", name);
        return accountRepository.findByCustomerNameIgnoreCase(name);
    }

    // Get accounts by branch ID
    public List<Account> getAccountsByBranch(Long branchId) {
        logger.info("Fetching accounts for branch with ID: {}", branchId);
        return accountRepository.findByBranchId(branchId);
    }

    // Add a new account with a transaction limit
    @Transactional
    public Account addAccount(Account account) {
        logger.info("Adding new account with customer name: {}", account.getCustomer());

        // Set default status if not provided
        if (account.getStatus() == null || account.getStatus().isEmpty()) {
            account.setStatus("ACTIVE");
            logger.info("Account status not provided. Defaulting to 'ACTIVE'");
        } else if (!VALID_STATUSES.contains(account.getStatus().toUpperCase())) {
            throw new IllegalArgumentException("Invalid account status: " + account.getStatus());
        } else {
            account.setStatus(account.getStatus().toUpperCase());
        }

        // Default balance if not provided
        if (account.getBalance() == null) {
            account.setBalance(BigDecimal.ZERO);
        }

        // Save the transaction limit first and associate it with the account
        if (account.getTransactionLimit() == null) {
            throw new IllegalArgumentException("Account must include a transaction limit");
        }
        TransactionLimit transactionLimit = transactionLimitRepository.save(account.getTransactionLimit());
        account.setTransactionLimit(transactionLimit);

        // Save the account
        Account savedAccount = accountRepository.save(account);
        logger.info("Successfully added new account with ID: {}", savedAccount.getId());
        return savedAccount;
    }

    // Get deactivated accounts
    public List<Account> getDeactivatedAccounts() {
        logger.info("Fetching deactivated accounts");
        return accountRepository.findByStatus("DEACTIVE");
    }

    // Update account status
    public void updateAccountStatus(Long accountId, String newStatus) {
        logger.info("Updating status of account with ID: {} to {}", accountId, newStatus);

        if (newStatus == null || !VALID_STATUSES.contains(newStatus.toUpperCase())) {
            throw new IllegalArgumentException("Invalid account status: " + newStatus);
        }

        // Fetch the account and update the status
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> {
                logger.error("Account not found with ID: {}", accountId);
                return new ResourceNotFoundException("Account not found with id: " + accountId);
            });

        account.setStatus(newStatus.toUpperCase());
        accountRepository.save(account);

        logger.info("Successfully updated account with ID: {} to status: {}", accountId, newStatus);
    }
}
