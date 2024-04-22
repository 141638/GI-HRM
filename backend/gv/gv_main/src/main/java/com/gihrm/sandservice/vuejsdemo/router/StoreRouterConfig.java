package com.gihrm.sandservice.vuejsdemo.router;

import com.gihrm.sandservice.vuejsdemo.service.StoreService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class StoreRouterConfig {
    /** API path for store API management */
    private static final String API_PATH = "/internal/store";

    @Bean
    RouterFunction<ServerResponse> storeRouterFunction(StoreService service) {
        return RouterFunctions.route().path(API_PATH, builder -> builder
            .POST("insert", service::insertStore)
            .PUT("update", service::updateStore)
            .GET("list", service::getStoreList)
            .GET("detail/{id}", service::getStoreDetail)
        ).build();
    }
}
