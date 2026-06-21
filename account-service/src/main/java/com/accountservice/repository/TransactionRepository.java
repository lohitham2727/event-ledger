package com.accountservice.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountservice.entity.Transaction;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountId(String accountId);
}
