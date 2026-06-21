package com.accountservice.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransactionRequest {

    private String eventId;

    private String accountId;

    private String type;

    private BigDecimal amount;
}
