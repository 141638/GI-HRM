package com.gi.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.CorsRegistration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurerComposite;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
	private final ReactiveAuthenticationManagerImpl authenticationManager;
	private final SecurityContextRepositoryImpl securityContextRepository;

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebFluxConfigurer corsConfigurer() {
		return new WebFluxConfigurerComposite() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				CorsRegistration corsConf = registry.addMapping("/api/**");
				corsConf.allowedOrigins(AuthConfigConstants.CORS_ALLOWED_ORIGINS);
				corsConf.maxAge(AuthConfigConstants.CORS_MAX_AGE);
				corsConf.allowedMethods(AuthConfigConstants.CORS_ALLOWED_METHODS);
			}
		};
	}

	@Bean
	public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
		http.cors();
		http.csrf(csrf -> csrf.disable());
		http.formLogin(form -> form.disable());
		http.httpBasic(httpBasic -> httpBasic.disable());
		http.authenticationManager(authenticationManager);
		http.securityContextRepository(securityContextRepository);
		http.authorizeExchange((authorize) -> authorize.pathMatchers(AuthConfigConstants.OPEN_API_ENDPOINTS).permitAll()
				.pathMatchers(AuthConfigConstants.AUTH_WHITELIST).permitAll().pathMatchers(HttpMethod.OPTIONS)
				.permitAll().pathMatchers(AuthConfigConstants.SYSTEM_STAFF_API).permitAll().anyExchange()
				.authenticated());
		return http.build();
	}
}
