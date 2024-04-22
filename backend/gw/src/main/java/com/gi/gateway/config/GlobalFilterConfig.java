package com.gi.gateway.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalFilterConfig {

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> {
            var request = exchange
                .getRequest()
                .mutate()
                .header("X-Custom-Header", "Test custom header")
                .build();
            return chain.filter(exchange.mutate().request(request).build());
        };
    }
}
