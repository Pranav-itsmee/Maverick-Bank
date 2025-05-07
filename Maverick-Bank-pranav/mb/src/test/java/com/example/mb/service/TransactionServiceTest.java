package com.example.mb.service;

import com.example.mb.model.Account;
import com.example.mb.model.Transaction;
import com.example.mb.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTransactions() {
        Transaction txn = new Transaction();
        txn.setId(1L);
        when(transactionRepository.findAll()).thenReturn(List.of(txn));

        List<Transaction> transactions = transactionService.getAllTransactions();
        assertEquals(1, transactions.size());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void testGetTransactionById() {
        Transaction txn = new Transaction();
        txn.setId(1L);
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(txn));

        Optional<Transaction> result = transactionService.getTransactionById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testGetTransactionsByAccount() {
        Transaction txn1 = new Transaction();
        txn1.setId(1L);
        Account acc = new Account();
        acc.setId(100L);
        txn1.setFromAccount(acc);

        when(transactionRepository.findByFromAccountId(100L)).thenReturn(List.of(txn1));

        List<Transaction> txns = transactionService.getTransactionsByAccount(100L);
        assertEquals(1, txns.size());
    }

    @Test
    void testGetTransactionsByType() {
        Transaction txn = new Transaction();
        txn.setTransactionType("UPI");

        when(transactionRepository.findByTransactionTypeIgnoreCase("UPI")).thenReturn(List.of(txn));

        List<Transaction> result = transactionService.getTransactionsByType("UPI");
        assertEquals("UPI", result.get(0).getTransactionType());
    }
}
