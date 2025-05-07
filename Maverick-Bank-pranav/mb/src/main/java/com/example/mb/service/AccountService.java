package com.example.mb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.mb.model.Account;
import com.example.mb.model.TransactionLimit;
import com.example.mb.repository.AccountRepository;
import com.example.mb.repository.TransactionLimitRepository;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class); // Logger initialization

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionLimitRepository transactionLimitRepository;

    // Constructor injection for AccountRepository (optional with field injection)
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
    public Account addAccount(Account account) {
        logger.info("Adding new account with customer name: {}", account.getCustomer());

        // Set default status if not provided
        if (account.getStatus() == null || account.getStatus().isEmpty()) {
            account.setStatus("active");
            logger.info("Account status not provided. Defaulting to 'active'");
        }

        // Save the transaction limit first and associate it with the account
        TransactionLimit transactionLimit = account.getTransactionLimit();
        transactionLimit = transactionLimitRepository.save(transactionLimit);
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
        
        // Fetch the account and update the status
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> {
                logger.error("Account not found with ID: {}", accountId);
                return new RuntimeException("Account not found");
            });
        
        account.setStatus(newStatus);
        accountRepository.save(account);
        
        logger.info("Successfully updated account with ID: {} to status: {}", accountId, newStatus);
    }
}
