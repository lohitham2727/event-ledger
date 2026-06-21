package com.accountservice.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.accountservice.dto.TransactionRequest;
import com.accountservice.entity.Transaction;
import com.accountservice.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	
	 private static final Logger log =
	            LoggerFactory.getLogger(AccountService.class);

    private final TransactionRepository repository;

    public void applyTransaction(TransactionRequest request) {
    	
    	log.info("Applying transaction for account {}",
                request.getAccountId());

        Transaction transaction = new Transaction();

        transaction.setEventId(request.getEventId());
        transaction.setAccountId(request.getAccountId());
        transaction.setType(request.getType());
        transaction.setAmount(request.getAmount());
        transaction.setTimestamp(Instant.now());

        repository.save(transaction);
        log.info("Transaction saved successfully for event {}",
                request.getEventId());
    }

    public BigDecimal getBalance(String accountId) {

        List<Transaction> transactions =
                repository.findByAccountId(accountId);

        BigDecimal credit =
                transactions.stream()
                        .filter(t -> "CREDIT".equalsIgnoreCase(t.getType()))
                        .map(Transaction::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal debit =
                transactions.stream()
                        .filter(t -> "DEBIT".equalsIgnoreCase(t.getType()))
                        .map(Transaction::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        return credit.subtract(debit);
    }

    public List<Transaction> getTransactions(String accountId) {
        return repository.findByAccountId(accountId);
    }
}