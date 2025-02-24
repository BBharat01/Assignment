package com.teya.assignment.ledger.service;

import com.teya.assignment.ledger.model.Transaction;
import com.teya.assignment.ledger.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LedgerServiceTest {

    @Mock
    private TransactionRepository transactionRepository; // Mock repository

    @InjectMocks
    private LedgerService ledgerService; // Inject mock into service

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDepositSuccess() {
        Transaction transaction = new Transaction("DEPOSIT", 100.0);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = ledgerService.recordTransaction("DEPOSIT", 100.0);

        assertEquals("DEPOSIT", result.getType());
        assertEquals(100.0, result.getAmount());

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testWithdrawSuccess() {
        List<Transaction> transactions = new ArrayList<>();

        // Adding transactions to the list
        transactions.add(new Transaction("WITHDRAWAL", 10.0));
        transactions.add(new Transaction("DEPOSIT", 100.0));
        transactions.add(new Transaction("WITHDRAWAL", 30.0));

        when(transactionRepository.findAll()).thenReturn(transactions);

        Transaction transaction = new Transaction("WITHDRAWAL", 50.0);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = ledgerService.recordTransaction("WITHDRAWAL", 50.0);

        assertEquals("WITHDRAWAL", result.getType());
        assertEquals(50.0, result.getAmount());

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testWithdrawInsufficientFunds() {

        List<Transaction> transactions = new ArrayList<>();

        // Adding transactions to the list
        transactions.add(new Transaction("WITHDRAWAL", 70.0));
        transactions.add(new Transaction("DEPOSIT", 100.0));
        transactions.add(new Transaction("WITHDRAWAL", 30.0));

        when(transactionRepository.findAll()).thenReturn(transactions);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ledgerService.recordTransaction("WITHDRAWAL", 50.0);
        });

        assertEquals("Insufficient balance", exception.getMessage());
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void testDepositNegativeAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ledgerService.recordTransaction("DEPOSIT", -100.0);
        });

        assertEquals("Amount must be positive", exception.getMessage());
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void testWithdrawNegativeAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ledgerService.recordTransaction("WITHDRAWAL", -50.0);
        });

        assertEquals("Amount must be positive", exception.getMessage());
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void testTransactionHistory() {
        List<Transaction> mockTransactions = Arrays.asList(
                new Transaction("DEPOSIT", 200.0),
                new Transaction("WITHDRAWAL", 50.0)
        );

        when(transactionRepository.findAll()).thenReturn(mockTransactions);

        List<Transaction> transactions = ledgerService.getTransactionHistory();

        assertEquals(2, transactions.size());
        assertEquals("DEPOSIT", transactions.get(0).getType());
        assertEquals(200.0, transactions.get(0).getAmount());
        assertEquals("WITHDRAWAL", transactions.get(1).getType());
        assertEquals(50.0, transactions.get(1).getAmount());

        verify(transactionRepository, times(1)).findAll();
    }
}
