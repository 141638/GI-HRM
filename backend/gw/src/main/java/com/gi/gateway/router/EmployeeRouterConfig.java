package com.gi.gateway.router;

import com.gi.gateway.service.EmployeeGatewayService;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.RouterFunctions.Builder;

@Configuration
public class EmployeeRouterConfig {
	private final String endpoint = "/api/resource/employee/";

	@Bean
	public RouterFunction<ServerResponse> employeeRouterFunction(EmployeeGatewayService employeeService) {
		Consumer<Builder> builderConsumer = builder -> {
			builder.POST("search", employeeService::searchEmployee);
			builder.POST("add", employeeService::addEmployee);
			builder.GET("details", employeeService::detailsEmployee);
			builder.DELETE("delete", employeeService::deleteEmployee);
		};
		return RouterFunctions.route().path(endpoint, builderConsumer).build();
	}
}
