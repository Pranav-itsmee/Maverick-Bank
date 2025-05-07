package com.example.mb.service;

import com.example.mb.model.Account;
import com.example.mb.model.Branch;
import com.example.mb.model.Customer;
import com.example.mb.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAccounts() {
        Account acc = new Account();
        acc.setId(1L);
        acc.setAccountNumber("ACC123");

        when(accountRepository.findAll()).thenReturn(List.of(acc));

        List<Account> accounts = accountService.getAllAccounts();
        assertEquals(1, accounts.size());
    }

    @Test
    void testGetAccountById() {
        Account acc = new Account();
        acc.setId(1L);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(acc));

        Optional<Account> result = accountService.getAccountById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testGetAccountsByCustomerName() {
        Account acc = new Account();
        Customer customer = new Customer();
        customer.setName("John");
        acc.setCustomer(customer);

        when(accountRepository.findByCustomerNameIgnoreCase("john")).thenReturn(List.of(acc));

        List<Account> accounts = accountService.getAccountsByCustomerName("john");
        assertEquals("John", accounts.get(0).getCustomer().getName());
    }

    @Test
    void testGetAccountsByBranch() {
        Account acc = new Account();
        Branch branch = new Branch();
        branch.setId(10L);
        acc.setBranch(branch);

        when(accountRepository.findByBranchId(10L)).thenReturn(List.of(acc));

        List<Account> result = accountService.getAccountsByBranch(10L);
        assertEquals(10L, result.get(0).getBranch().getId());
    }
}
