package com.event.gateway.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.atomic.AtomicInteger;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.stereotype.Service;

import com.event.gateway.client.AccountClient;
import com.event.gateway.dto.EventRequest;
import com.event.gateway.dto.TransactionRequest;
import com.event.gateway.entity.Event;
import com.event.gateway.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
	private static final Logger log =
            LoggerFactory.getLogger(EventService.class);

    private final EventRepository repository;
    
    private final AtomicInteger eventCounter =
            new AtomicInteger();

    private final AccountClient accountClient;
    
    @CircuitBreaker(
            name = "accountService",
            fallbackMethod = "fallbackCreateEvent")
    public Event createEvent(EventRequest request) {
    	log.info("Received eventId={}, accountId={}, type={}, amount={}",
                request.getEventId(),
                request.getAccountId(),
                request.getType(),
                request.getAmount());

        if (repository.existsById(request.getEventId())) {
        	 log.info("Duplicate event detected: {}",
        	            request.getEventId());

            return repository.findById(
                    request.getEventId())
                    .orElseThrow();
        }

        Event event = new Event();

        event.setEventId(request.getEventId());
        event.setAccountId(request.getAccountId());
        event.setType(request.getType());
        event.setAmount(request.getAmount());
        event.setCurrency(request.getCurrency());
        event.setEventTimestamp(
                request.getEventTimestamp());

        repository.save(event);
        log.info("Event saved successfully: {}",
                event.getEventId());
        
        eventCounter.incrementAndGet();

        TransactionRequest tx =
                new TransactionRequest();

        tx.setEventId(request.getEventId());
        tx.setAccountId(request.getAccountId());
        tx.setType(request.getType());
        tx.setAmount(request.getAmount());
        
        log.info("Calling account-service for eventId={}",
                event.getEventId());

        accountClient.applyTransaction(
                request.getAccountId(),
                tx);
        log.info("Transaction applied successfully for account={}",
                request.getAccountId());

        return event;
    }
    
    public int getProcessedEventCount() {
        return eventCounter.get();
    }

    public Event getEvent(String id) {

        return repository.findById(id)
                .orElseThrow();
    }

    public List<Event> getEvents(
            String accountId) {

        return repository
                .findByAccountIdOrderByEventTimestampAsc(
                        accountId);
    }
    
    public Event fallbackCreateEvent(
            EventRequest request,
            Exception ex) {

        log.error(
                "Account Service unavailable. Fallback triggered for event {}",
                request.getEventId());

        Event event = new Event();

        event.setEventId(request.getEventId());
        event.setAccountId(request.getAccountId());
        event.setType(request.getType());
        event.setAmount(request.getAmount());
        event.setCurrency(request.getCurrency());
        event.setEventTimestamp(request.getEventTimestamp());

        return event;
    }
}