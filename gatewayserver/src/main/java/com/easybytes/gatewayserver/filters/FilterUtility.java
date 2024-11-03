package com.easybytes.gatewayserver.filters;

import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.net.http.HttpRequest;
import java.util.List;

@Configuration
public class FilterUtility {

    public static final String CORRELATION_ID = "easybank-correlation-id";

    public String getCorrelationId(HttpHeaders request){
        if(request.get(CORRELATION_ID) != null){
            List<String> correlationId = request.get(CORRELATION_ID);
            return correlationId.stream().findFirst().get();
        }else {
            return null;
        }
    }

    public ServerWebExchange setCorrelationID(ServerWebExchange exchange,String correlationId){
        return exchange.mutate().request(exchange.getRequest().mutate().header(CORRELATION_ID,correlationId).build()).build();
    }
}
