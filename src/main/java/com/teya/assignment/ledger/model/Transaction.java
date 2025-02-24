package com.teya.assignment.ledger.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // "DEPOSIT" or "WITHDRAWAL"
    private double amount;
    private LocalDateTime timestamp;

    public Transaction() {}

    public Transaction(String type, double amount) {
        this.type = type.toUpperCase();
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public String getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
}

