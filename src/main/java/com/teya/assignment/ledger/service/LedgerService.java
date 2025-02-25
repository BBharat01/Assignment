package com.teya.assignment.ledger.service;

import com.teya.assignment.ledger.model.Transaction;
import com.teya.assignment.ledger.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LedgerService {

    private static final Logger log = LoggerFactory.getLogger(LedgerService.class);
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction recordTransaction(String type, double amount) {
        if (amount <= 0) {
            log.error("Value of Amount should be positive , current value is {}",amount);
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (type.equalsIgnoreCase("WITHDRAWAL") && getBalance() < amount) {
            log.error("Balance is less than the amount requested, " +
                    "current balance is {} and amount requested is {}",getBalance(),amount);
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

