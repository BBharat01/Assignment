package com.teya.assignment.ledger.controller;

import com.teya.assignment.ledger.model.Transaction;
import com.teya.assignment.ledger.service.LedgerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(LedgerController.class)
class LedgerControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private LedgerService ledgerService;

    @InjectMocks
    private LedgerController ledgerController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ledgerController).build();
    }

    @Test
    void testDepositSuccess() throws Exception {
        Transaction mockTransaction = new Transaction("DEPOSIT", 100.0);

        when(ledgerService.recordTransaction("DEPOSIT", 100.0)).thenReturn(mockTransaction);

        mockMvc.perform(post("/ledger/deposit?amount=100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("DEPOSIT"))
                .andExpect(jsonPath("$.amount").value(100.0));
    }

    @Test
    void testWithdrawSuccess() throws Exception {
        Transaction mockTransaction = new Transaction("WITHDRAWAL", 50.0);

        when(ledgerService.recordTransaction("WITHDRAWAL", 50.0)).thenReturn(mockTransaction);

        mockMvc.perform(post("/ledger/withdraw?amount=50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("WITHDRAWAL"))
                .andExpect(jsonPath("$.amount").value(50.0));
    }

    @Test
    void testDepositNegativeAmount() throws Exception {
        when(ledgerService.recordTransaction("DEPOSIT", -100.0)).thenThrow(new IllegalArgumentException("Amount must be positive"));

        assertThrows(Exception.class,
                ()->{mockMvc.perform(post("/ledger/deposit?amount=-100"));});
    }

    @Test
    void testWithdrawInsufficientFunds() throws Exception {
        when(ledgerService.recordTransaction("WITHDRAWAL", 500.0)).thenThrow(new IllegalArgumentException("Insufficient balance"));

        assertThrows(Exception.class,
                ()->{mockMvc.perform(post("/ledger/withdraw?amount=500"));});
    }

    @Test
    void testGetBalance() throws Exception {
        when(ledgerService.getBalance()).thenReturn(200.0);

        mockMvc.perform(get("/ledger/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string("200.0"));
    }

    @Test
    void testGetTransactionHistory() throws Exception {
        List<Transaction> mockHistory = List.of(
                new Transaction("DEPOSIT", 100.0),
                new Transaction("WITHDRAWAL", 50.0)
        );

        when(ledgerService.getTransactionHistory()).thenReturn(mockHistory);

        mockMvc.perform(get("/ledger/history"))
                .andExpect(status().isOk());
    }
}
