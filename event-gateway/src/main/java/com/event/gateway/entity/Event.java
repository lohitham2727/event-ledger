package com.event.gateway.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "events")
public class Event {

    @Id
    private String eventId;

    private String accountId;

    private String type;

    private BigDecimal amount;

    private String currency;

    private Instant eventTimestamp;

    @Lob
    private String metadata;
}
