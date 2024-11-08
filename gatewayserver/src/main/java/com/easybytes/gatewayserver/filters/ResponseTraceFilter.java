package com.easybytes.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.net.http.HttpRequest;

@Configuration
public class ResponseTraceFilter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);

    @Autowired
    private FilterUtility filterUtility;

    @Bean
    public GlobalFilter postResponseFilter() {
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
                     HttpHeaders headers = exchange.getRequest().getHeaders();
                     String correlationId = filterUtility.getCorrelationId(headers);
                     if(!(exchange.getResponse().getHeaders().containsKey(FilterUtility.CORRELATION_ID))){
                         logger.debug("Updated the correlation id to the outbound headers: {}", correlationId);
                         exchange.getResponse().getHeaders().add(FilterUtility.CORRELATION_ID,correlationId);

                     }
        }));
    }
}
