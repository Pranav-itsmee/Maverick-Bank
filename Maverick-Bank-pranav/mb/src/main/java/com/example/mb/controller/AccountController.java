package com.example.mb.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mb.model.Account;
import com.example.mb.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "http://localhost:5173")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    /**
     * Get all accounts.
     * Logs the request and the number of accounts fetched.
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<Account>> getAllAccounts() {
        logger.info("GET /api/accounts/getAll called");
        List<Account> accounts = accountService.getAllAccounts();
        logger.info("Fetched {} accounts", accounts.size());
        return ResponseEntity.ok(accounts);
    }

    /**
     * Get account by ID.
     * Logs the request and the outcome (found or not found).
     */
    @GetMapping("/{id}")
    public Optional<Account> getAccountById(@PathVariable("id") Long id) {
        logger.info("GET /api/accounts/{} called", id);
        Optional<Account> account = accountService.getAccountById(id);
        if (account.isPresent()) {
            logger.info("Account found: {}", account.get());
        } else {
            logger.warn("Account with ID {} not found", id);
        }
        return account;
    }

    /**
     * Get accounts by customer name.
     * Logs the request and the number of accounts found for the customer.
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Account>> getAccountByName(@PathVariable String name) {
        logger.info("GET /api/accounts/name/{} called", name);
        List<Account> accounts = accountService.getAccountsByCustomerName(name);
        logger.info("Found {} accounts for customer name '{}'", accounts.size(), name);
        return ResponseEntity.ok(accounts);
    }

    /**
     * Get accounts by branch ID.
     * Logs the request and the number of accounts found for the branch.
     */
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<Account>> getAccountByBranch(@PathVariable Long branchId) {
        logger.info("GET /api/accounts/branch/{} called", branchId);
        List<Account> accounts = accountService.getAccountsByBranch(branchId);
        logger.info("Found {} accounts for branch ID {}", accounts.size(), branchId);
        return ResponseEntity.ok(accounts);
    }

    /**
     * Add a new account.
     * Logs the request and the account information.
     * Logs the ID of the newly created account.
     */
    @PostMapping("/add")
    public ResponseEntity<Account> addAccount(@RequestBody Account account) {
        logger.info("POST /api/accounts/add called with account: {}", account);
        Account savedAccount = accountService.addAccount(account);
        logger.info("Account created with ID {}", savedAccount.getId());
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    /**
     * Get all deactivated accounts.
     * Logs the request and the number of deactivated accounts found.
     */
    @GetMapping("/getDeactivated")
    public List<Account> getDeactivatedAccounts() {
        logger.info("GET /api/accounts/getDeactivated called");
        List<Account> deactivatedAccounts = accountService.getDeactivatedAccounts();
        logger.info("Found {} deactivated accounts", deactivatedAccounts.size());
        return deactivatedAccounts;
    }

    /**
     * Update account status.
     * Logs the request with account ID and status.
     * Logs after the status update.
     */
    @PutMapping("/{accountId}/status")
    public ResponseEntity<?> updateAccountStatus(@PathVariable("accountId") Long accountId,
                                                 @RequestBody Map<String, String> payload) {
        String status = payload.get("status");
        logger.info("PUT /api/accounts/{}/status called with status: {}", accountId, status);
        accountService.updateAccountStatus(accountId, status);
        logger.info("Updated status of account ID {} to '{}'", accountId, status);
        return ResponseEntity.ok().build();
    }
}
