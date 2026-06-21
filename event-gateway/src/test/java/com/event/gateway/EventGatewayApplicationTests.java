package com.event.gateway;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.event.gateway.service.EventService;

@SpringBootTest
class EventGatewayApplicationTests {

	 @Autowired
	    private EventService service;

	    @Test
	    void serviceLoads() {
	        assertNotNull(service);
	    }
}
