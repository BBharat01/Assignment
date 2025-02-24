package com.teya.assignment.ledger.controller;

import com.teya.assignment.ledger.model.Transaction;
import com.teya.assignment.ledger.service.LedgerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ledger")
@Tag(name = "Ledger API", description = "APIs for managing ledger transactions")
public class LedgerController {

    @Autowired
    private LedgerService ledgerService;

    @PostMapping("/deposit")
    @Operation(summary = "Deposit Money", description = "Deposits a specified amount into the ledger")
    public Transaction deposit(@RequestParam double amount) {
        return ledgerService.recordTransaction("DEPOSIT", amount);
    }

    @PostMapping("/withdraw")
    @Operation(summary = "Withdraw Money", description = "Withdraws a specified amount from the ledger")
    public Transaction withdraw(@RequestParam double amount) {
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

