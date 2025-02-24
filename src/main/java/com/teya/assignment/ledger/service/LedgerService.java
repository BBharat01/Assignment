package com.teya.assignment.ledger.service;

import com.teya.assignment.ledger.model.Transaction;
import com.teya.assignment.ledger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LedgerService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction recordTransaction(String type, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (type.equalsIgnoreCase("WITHDRAWAL") && getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        Transaction transaction = new Transaction(type, amount);
        return transactionRepository.save(transaction);
    }

    public double getBalance() {
        return transactionRepository.findAll().stream()
                .mapToDouble(t -> t.getType().equals("DEPOSIT") ? t.getAmount() : -t.getAmount())
                .sum();
    }

    public List<Transaction> getTransactionHistory() {
        return transactionRepository.findAll();
    }
}

