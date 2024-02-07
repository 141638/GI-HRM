package com.gi.gateway.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.gi.gateway.exception.BadCredentialsException;
import com.gi.gateway.security.jwt.RouterValidator;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@AllArgsConstructor
public class SecurityContextRepositoryImpl implements ServerSecurityContextRepository {
	private ReactiveAuthenticationManagerImpl authenticationManager;
	private RouterValidator routerValidator;

	@Override
	public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();
		if (Boolean.TRUE.equals(routerValidator.isSecured(request))
		        && !request.getMethod().equals(HttpMethod.OPTIONS)) {
			HttpHeaders headers = request.getHeaders();
			return Mono.just(Objects.requireNonNull(headers.getFirst(HttpHeaders.AUTHORIZATION)))
			        .switchIfEmpty(Mono.error(new BadCredentialsException("authorization not found")))
			        .filter(authHeader -> authHeader.startsWith("Bearer ")).flatMap(authHeader -> {
				        String jwtToken = authHeader.substring(7);
				        Authentication auth = new UsernamePasswordAuthenticationToken(jwtToken, jwtToken);
				        return this.authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
			        });
		}
		return Mono.empty();
	}
}
