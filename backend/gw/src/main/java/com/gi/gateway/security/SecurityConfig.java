package com.gi.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.FormLoginSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpBasicSpec;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.gi.gateway.config.CorsConfigurationSourceImpl;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
	private final ReactiveAuthenticationManagerImpl authenticationManager;
	private final SecurityContextRepositoryImpl securityContextRepository;
	private final CorsConfigurationSourceImpl corsConfig;

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
		http.cors(t -> t.configurationSource(corsConfig));
		http.csrf(CsrfSpec::disable);
		http.formLogin(FormLoginSpec::disable);
		http.httpBasic(HttpBasicSpec::disable);
		http.authenticationManager(authenticationManager);
		http.securityContextRepository(securityContextRepository);
		http.authorizeExchange(authorize -> authorize.pathMatchers(AuthConfigConstants.OPEN_API_ENDPOINTS).permitAll()
		        .pathMatchers(AuthConfigConstants.AUTH_WHITELIST).permitAll().pathMatchers(HttpMethod.OPTIONS)
		        .permitAll().pathMatchers(AuthConfigConstants.SYSTEM_STAFF_API).permitAll().anyExchange()
		        .authenticated());
		return http.build();
	}
}
