package com.gi.gateway.config;

import java.util.stream.Stream;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;

import com.gi.gateway.security.AuthConfigConstants;

@Configuration
public class CorsConfigurationSourceImpl implements CorsConfigurationSource {

	@Override
	public CorsConfiguration getCorsConfiguration(ServerWebExchange exchange) {
		CorsConfiguration corsConfig = new CorsConfiguration();

		Stream.of(AuthConfigConstants.CORS_ALLOWED_ORIGINS).forEach(corsConfig::addAllowedOrigin);
		Stream.of(AuthConfigConstants.CORS_ALLOWED_METHODS).forEach(corsConfig::addAllowedMethod);
		corsConfig.addAllowedHeader("*");
		corsConfig.setMaxAge(AuthConfigConstants.CORS_MAX_AGE);
		return corsConfig;
	}

}
