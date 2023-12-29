package com.gi.hrm.service.category;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public interface CategoryServiceHandler {
	public Mono<ServerResponse> upsert(ServerRequest request);

	public Mono<ServerResponse> list(ServerRequest request);

	public Mono<ServerResponse> delete(ServerRequest request);

	public Mono<ServerResponse> dropdownCategoryByWorkspaceId(ServerRequest request);

	public Mono<ServerResponse> fluxStreamTest(ServerRequest request);
}
