package com.gi.hrm.task.presentation;

import com.gi.hrm.task.application.ProjectRouterHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.RouterFunctions.Builder;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.function.Consumer;

@Configuration
public class ProjectRouterConfig {

    @Bean
    RouterFunction<ServerResponse> projectRouterFunction(ProjectRouterHandler handler) {
        Consumer<Builder> consumerBuilder = builder -> {
            builder.GET("", handler::findAll);
            builder.GET("/{projectId}", handler::findById);
            builder.POST("", handler::create);
            builder.PUT("", handler::change);
            builder.DELETE("/{projectId}", handler::delete);
        };

        return RouterFunctions.route().path("/api/project", consumerBuilder).build();
    }
}
