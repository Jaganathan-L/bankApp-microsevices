package com.easybytes.gatewayserver.filters;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;

@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

    private static final Logger requestTraceFilterLogger = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Autowired
    private FilterUtility filterUtility;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        if(isCorrelationIdPresent(headers)){
            requestTraceFilterLogger.debug("easyBank-correlation-id found in RequestTraceFilter : {}",
                    filterUtility.getCorrelationId(headers));
        }else {
            String correlationId = generateCorrelation();
            exchange = filterUtility.setCorrelationID(exchange,correlationId);
            requestTraceFilterLogger.debug("easyBank-correlation-id generated in RequestTraceFilter : {}",
                    correlationId);
        }
        return chain.filter(exchange);
    }

    private boolean isCorrelationIdPresent(HttpHeaders headers) {

        return filterUtility.getCorrelationId(headers) != null;
    }

    private String generateCorrelation() {
        return  java.util.UUID.randomUUID().toString();
    }
}
