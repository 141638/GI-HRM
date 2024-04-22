package com.gihrm.sandservice.vuejsdemo.auth;

import com.gihrm.sandservice.vuejsdemo.dto.request.ServiceTokenDto;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements WebFilter {
    public AuthFilter() {
        // NoArgumentsConstructor
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        return chain.filter(exchange).contextWrite(ctx -> {
            ServiceTokenDto tokenDto = new ServiceTokenDto();
            if (StringUtils.hasText(token)) {
                tokenDto = JwtUtils.toServiceTokenDto(token.replace("Bearer ", ""));
            }
            return ctx.put("tokenDto", tokenDto);
        });
    }
}
