package com.accountservice.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accountservice.dto.TransactionRequest;
import com.accountservice.entity.Transaction;
import com.accountservice.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{accountId}/transactions")
    public ResponseEntity<String> applyTransaction(
            @PathVariable String accountId,
            @RequestBody TransactionRequest request) {

        accountService.applyTransaction(request);

        return ResponseEntity.ok("Transaction Applied");
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BigDecimal> getBalance(
            @PathVariable String accountId) {

        return ResponseEntity.ok(
                accountService.getBalance(accountId));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<List<Transaction>> getAccount(
            @PathVariable String accountId) {

        return ResponseEntity.ok(
                accountService.getTransactions(accountId));
    }
}