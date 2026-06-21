# Event Ledger Assessment

## Overview

This project consists of two Spring Boot microservices:

1. Event Gateway Service
2. Account Service

The system accepts financial events and updates account balances.

---

## Technologies Used

* Java 17
* Spring Boot 3
* Spring Data JPA
* H2 Database
* OpenFeign
* Spring Boot Actuator
* Lombok
* Maven

---

## Services

### Event Gateway

Port: 8080

Responsibilities:

* Accept event requests
* Idempotency handling
* Forward transactions to Account Service

### Account Service

Port: 8081

Responsibilities:

* Store transactions
* Calculate account balance
* Expose balance API

---

## APIs

### Create Event

POST `/events`

Request:

```json
{
  "eventId":"evt-001",
  "accountId":"acct-123",
  "type":"CREDIT",
  "amount":100,
  "currency":"USD",
  "eventTimestamp":"2026-05-15T14:02:11Z"
}
```

### Get Balance

GET `/accounts/acct-123/balance`

---

## Observability

Implemented:

* Structured logging
* Trace ID tracking
* Spring Boot Actuator

Health Endpoint:

`/actuator/health`

---

## Resiliency

Implemented:

* Fallback handling when Account Service is unavailable
* Error logging

---

## Running the Application

### Start Account Service

```bash
cd account-service
mvn spring-boot:run
```

### Start Event Gateway

```bash
cd event-gateway
mvn spring-boot:run
```

---

## Author

Lohith A M
