package com.gi.gateway.router.config.staff;

import com.gi.gateway.common.RouterConstants;
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

	@Bean
	RouterFunction<ServerResponse> employeeRouterFunction(EmployeeGatewayService employeeService) {
		Consumer<Builder> builderConsumer = builder -> {
			builder.POST("search", employeeService::searchEmployee);
			builder.POST("add", employeeService::addEmployee);
			builder.GET("details", employeeService::detailsEmployee);
			builder.DELETE("delete", employeeService::deleteEmployee);
			builder.GET("dropdown", employeeService::dropdown);
		};
		return RouterFunctions.route().path(RouterConstants.EMPLOYEE_LIST, builderConsumer).build();
	}
}
