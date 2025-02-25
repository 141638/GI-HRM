package com.gi.hrm.task.application.workspace;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface WorkspaceRouterHandler {
    Mono<ServerResponse> search(ServerRequest request);

    Mono<ServerResponse> upsert(ServerRequest request);

    Mono<ServerResponse> delete(ServerRequest request);
}
