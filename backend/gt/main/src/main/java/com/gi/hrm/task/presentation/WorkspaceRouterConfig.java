package com.gi.hrm.task.presentation;

import com.gi.hrm.task.application.workspace.WorkspaceRouterHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.RouterFunctions.Builder;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.function.Consumer;

@Configuration
public class WorkspaceRouterConfig {

    @Bean
    RouterFunction<ServerResponse> workspaceRouterFunction(WorkspaceRouterHandler handler) {
        Consumer<Builder> consumerBuilder = builder -> {
            builder.GET("search", handler::search);
            builder.PUT("upsert", handler::upsert);
            builder.DELETE("delete", handler::delete);
        };

        return RouterFunctions.route().path("/api/workspace/", consumerBuilder).build();
    }
}
