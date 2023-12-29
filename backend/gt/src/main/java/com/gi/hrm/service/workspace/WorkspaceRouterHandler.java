package com.gi.hrm.service.workspace;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public interface WorkspaceRouterHandler {
	Mono<ServerResponse> search(ServerRequest request);

	Mono<ServerResponse> upsert(ServerRequest request);

	Mono<ServerResponse> delete(ServerRequest request);
}
