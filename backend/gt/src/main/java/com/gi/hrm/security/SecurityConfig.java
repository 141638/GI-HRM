package com.gi.hrm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.FormLoginSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpBasicSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.gi.hrm.config.internal.CorsConfigurationSourceImpl;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
	private final CorsConfigurationSourceImpl corsConfig;

	@Bean
	SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
		http.cors(t -> t.configurationSource(corsConfig));
		http.csrf(CsrfSpec::disable);
		http.formLogin(FormLoginSpec::disable);
		http.httpBasic(HttpBasicSpec::disable);
		http.authorizeExchange(auth -> auth.pathMatchers(AuthConfigConstants.TEST_API).permitAll()
		        .pathMatchers(HttpMethod.OPTIONS).permitAll().anyExchange().authenticated());
		return http.build();
	}

}
