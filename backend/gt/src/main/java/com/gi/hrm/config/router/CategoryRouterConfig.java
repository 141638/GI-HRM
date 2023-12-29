package com.gi.hrm.config.router;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.RouterFunctions.Builder;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.gi.hrm.common.CommonRouterProperties;
import com.gi.hrm.service.category.CategoryServiceHandler;

@Configuration
public class CategoryRouterConfig extends CommonRouterProperties {

	@Bean
	RouterFunction<ServerResponse> categoryRouterFunction(CategoryServiceHandler categoryHandler) {
		Consumer<Builder> consumerBuilder = builder -> {
			builder.GET("list", categoryHandler::list);
			builder.PUT("upsert", categoryHandler::upsert);
			builder.DELETE("delete", categoryHandler::delete);
			builder.GET("dropdown-by-workspace-id", categoryHandler::dropdownCategoryByWorkspaceId);
			builder.GET("flux-stream-test", categoryHandler::fluxStreamTest);
		};

		return RouterFunctions.route().path(CATEGORY_ENDPOINT, consumerBuilder).build();
	}
}
