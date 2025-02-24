package com.teya.assignment.ledger.repository;

import com.teya.assignment.ledger.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> { }

