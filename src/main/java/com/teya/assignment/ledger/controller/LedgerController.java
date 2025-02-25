package com.teya.assignment.ledger.controller;

import com.teya.assignment.ledger.model.Transaction;
import com.teya.assignment.ledger.service.LedgerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ledger")
@Tag(name = "Ledger API", description = "APIs for managing ledger transactions")
public class LedgerController {

    private static final Logger log = LoggerFactory.getLogger(LedgerController.class);
    @Autowired
    private LedgerService ledgerService;

    @PostMapping("/deposit")
    @Operation(summary = "Deposit Money", description = "Deposits a specified amount into the ledger")
    public Transaction deposit(@RequestParam double amount) {
        log.info("Depositing {} into account",amount);
        return ledgerService.recordTransaction("DEPOSIT", amount);
    }

    @PostMapping("/withdraw")
    @Operation(summary = "Withdraw Money", description = "Withdraws a specified amount from the ledger")
    public Transaction withdraw(@RequestParam double amount) {
        log.info("Withdrawing {} from account",amount);
        return ledgerService.recordTransaction("WITHDRAWAL", amount);
    }

    @GetMapping("/balance")
    @Operation(summary = "Get Balance", description = "Retrieves the current account balance")
    public double getBalance() {
        return ledgerService.getBalance();
    }

    @GetMapping("/history")
    @Operation(summary = "Transaction History", description = "Returns the list of all transactions")
    public List<Transaction> getTransactionHistory() {
        return ledgerService.getTransactionHistory();
    }
}

