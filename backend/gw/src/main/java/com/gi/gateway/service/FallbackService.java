package com.gi.gateway.service;

import com.gi.gateway.dto.response.common.PreBuiltServerResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class FallbackService {
    public Mono<ServerResponse> fallbackHandler(ServerRequest serverRequest) {
        return PreBuiltServerResponse.badRequest("Global fallback response triggered", String.class);
    }
}
