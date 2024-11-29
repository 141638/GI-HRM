package com.gi.hrm.config.internal;

import com.gi.hrm.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class SecurityContextRepositoryImpl implements ServerSecurityContextRepository {
    /** Token prefix */
    private static final String AUTHENTICATION_TOKEN_PREFIX = "Bearer ";
    /** Class configuration handles authentication */
    private ReactiveAuthenticationManagerImpl authenticationManager;

    /**
     * Saves the SecurityContext
     *
     * @param exchange the exchange to associate to the {@link SecurityContext}
     * @param context the {@link SecurityContext} to save
     * @return method is not supported
     */
    @Override
    public Mono<Void> save(final ServerWebExchange exchange, final SecurityContext context) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Loads the SecurityContext associated with the {@link ServerWebExchange}
     *
     * @param exchange the exchange to associate to the {@link SecurityContext}
     * @return Mono object of SecurityContext if the user token is valid and authentication can be retrieved, else empty
     */
    @Override
    public Mono<SecurityContext> load(final ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        if (!request.getMethod().equals(HttpMethod.OPTIONS)) {
            return Mono.just(extractToken(request))
                    .switchIfEmpty(Mono.error(new BadCredentialsException("authorization not found")))
                    .filter(authHeader -> authHeader.startsWith(AUTHENTICATION_TOKEN_PREFIX)).flatMap(authHeader -> {
                        String jwtToken = authHeader.substring(AUTHENTICATION_TOKEN_PREFIX.length());
                        if (ValidationUtils.isNullOrEmpty(jwtToken) || jwtToken.equals("null")) {
                            return Mono.empty();
                        }
                        Authentication auth = new UsernamePasswordAuthenticationToken(jwtToken, jwtToken);
                        return this.authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
                    });
        }
        return Mono.empty();
    }

    /**
     * Extract JWT token from Http request header/param
     *
     * @param request Http request
     * @return JWT token
     */
    private String extractToken(final ServerHttpRequest request) {
        String token;
        if (request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        } else {
            token = AUTHENTICATION_TOKEN_PREFIX + request.getQueryParams().getFirst(HttpHeaders.AUTHORIZATION);
        }
        if (StringUtils.hasText(token)) {
            return token;
        }
        throw new BadCredentialsException("token not found");
    }
}
