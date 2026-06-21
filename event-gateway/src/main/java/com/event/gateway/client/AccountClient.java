package com.event.gateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.event.gateway.dto.TransactionRequest;

@FeignClient(
        name = "account-service",
        url = "${account.service.url}")
public interface AccountClient {

    @PostMapping("/accounts/{accountId}/transactions")
    String applyTransaction(
            @PathVariable String accountId,
            @RequestBody TransactionRequest request);
}