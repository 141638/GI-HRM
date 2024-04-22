package com.gi.hrm.config.internal;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;

@Configuration
public class CorsConfigurationSourceImpl implements CorsConfigurationSource {

	@Override
	public CorsConfiguration getCorsConfiguration(ServerWebExchange exchange) {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.addAllowedOrigin("http://localhost:4201");
		corsConfig.addAllowedOrigin("http://localhost:8888");
		corsConfig.addAllowedHeader("*");
		corsConfig.addAllowedMethod("*");
		return corsConfig;
	}

}
