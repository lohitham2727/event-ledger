package com.event.gateway.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.event.gateway.dto.EventRequest;
import com.event.gateway.entity.Event;
import com.event.gateway.service.EventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @PostMapping("/events")
    public Event createEvent(
            @Valid
            @RequestBody EventRequest request) {

        return service.createEvent(request);
    }

    @GetMapping("/events/{id}")
    public Event getEvent(
            @PathVariable String id) {

        return service.getEvent(id);
    }

    @GetMapping("/events")
    public List<Event> getEvents(
            @RequestParam String account) {

        return service.getEvents(account);
    }
    @GetMapping("/metrics/events")
    public int eventCount() {
        return service.getProcessedEventCount();
    }
}