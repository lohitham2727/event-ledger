package com.event.gateway.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.event.gateway.entity.Event;

public interface EventRepository extends JpaRepository<Event, String> {

    List<Event> findByAccountIdOrderByEventTimestampAsc(
            String accountId);
}
