package com.gi.hrm.task.application.workspace;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class WorkspaceRouterHandlerImpl implements WorkspaceRouterHandler {
    private final WorkspaceService workspaceService;

    @Override
    public Mono<ServerResponse> search(ServerRequest request) {
        // return Mono.fromCallable(request::queryParams).flatMap(workspaceService::search);
        return Mono.empty();
    }

    @Override
    public Mono<ServerResponse> upsert(ServerRequest request) {
        // return request.bodyToMono(WorkspaceUpsertRequest.class).flatMap(workspaceService::upsert);
        return Mono.empty();
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest request) {
        // return Mono.fromCallable(request::queryParams).flatMap(workspaceService::delete);
        return Mono.empty();
    }
}
