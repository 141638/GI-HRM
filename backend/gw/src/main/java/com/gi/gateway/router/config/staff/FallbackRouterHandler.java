package com.gi.gateway.router.config.staff;

import com.gi.gateway.common.RouterMappingConstants;
import com.gi.gateway.service.FallbackService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.function.Consumer;

@Configuration
public class FallbackRouterHandler {
	@Bean("fallbackRouteHandler")
    RouterFunction<ServerResponse> fallbackFunction(FallbackService fallbackService) {
		Consumer<RouterFunctions.Builder> builderConsumer = builder -> builder.POST(fallbackService::fallbackHandler);
		return RouterFunctions.route().path(RouterMappingConstants.FALLBACK, builderConsumer).build();
	}
}
