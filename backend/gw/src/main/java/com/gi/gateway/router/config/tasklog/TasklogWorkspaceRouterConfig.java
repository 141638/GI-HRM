package com.gi.gateway.router.config.tasklog;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.RouterFunctions.Builder;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.gi.gateway.common.RouterConstants;
import com.gi.gateway.router.handler.tasklog.TasklogWorkspaceRouterHandler;

@Configuration
public class TasklogWorkspaceRouterConfig {
	@Bean("tasklogRouter")
	RouterFunction<ServerResponse> tasklogWorkspaceRouterConfiguration(TasklogWorkspaceRouterHandler handler) {
		Consumer<Builder> routerBuilder = builder -> {
			builder.GET("search", handler::search);
			builder.PUT("upsert", handler::upsert);

		};

		return RouterFunctions.route().path(RouterConstants.TASKLOG_WORKSPACE, routerBuilder).build();
	}
}
