package com.easybytes.gatewayserver.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;


public class RateLimiterConfig {

//    @Bean
//    public static RedisRateLimiter redisRateLimiter(){
//        return new RedisRateLimiter(1,1,1);
//    }
//
//    @Bean
//    public static KeyResolver userKeyResolver(){
//        return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
//                .defaultIfEmpty("anonymous");
//    }
}
