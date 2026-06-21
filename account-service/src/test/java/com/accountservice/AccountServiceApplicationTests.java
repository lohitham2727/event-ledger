package com.accountservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.accountservice.service.AccountService;

@SpringBootTest
class AccountServiceApplicationTests {

	@Autowired
    private AccountService service;

    @Test
    void balanceCalculation() {
        assertNotNull(service);
    }
}
