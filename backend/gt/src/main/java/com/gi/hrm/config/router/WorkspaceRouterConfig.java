package com.gi.hrm.config.router;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.RouterFunctions.Builder;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.gi.hrm.common.CommonRouterProperties;
import com.gi.hrm.service.workspace.WorkspaceRouterHandler;

@Configuration
public class WorkspaceRouterConfig extends CommonRouterProperties {

	@Bean
	RouterFunction<ServerResponse> workspaceRouterFunction(WorkspaceRouterHandler handler) {
		Consumer<Builder> consumerBuilder = builder -> {
			builder.GET("search", handler::search);
			builder.PUT("upsert", handler::upsert);
			builder.DELETE("delete", handler::delete);
		};

		return RouterFunctions.route().path(WORKSPACE_ENDPOINT, consumerBuilder).build();
	}
}
