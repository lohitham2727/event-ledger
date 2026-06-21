package com.event.gateway.config;

import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {

        return template -> {

            String traceId =
                    MDC.get("traceId");

            if (traceId != null) {
                template.header(
                        "X-Trace-Id",
                        traceId);
            }
        };
    }
}