package com.gi.hrm.service.workspace;

import com.gi.hrm.dto.request.workspace.WorkspaceUpsertRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class WorkspaceRouterHandlerImpl implements WorkspaceRouterHandler {
	private final WorkspaceService workspaceService;

	@Override
	public Mono<ServerResponse> search(ServerRequest request) {
		return Mono.fromCallable(request::queryParams).flatMap(workspaceService::search);
	}

	@Override
	public Mono<ServerResponse> upsert(ServerRequest request) {
		return request.bodyToMono(WorkspaceUpsertRequest.class).flatMap(workspaceService::upsert);
	}

	@Override
	public Mono<ServerResponse> delete(ServerRequest request) {
		return Mono.fromCallable(request::queryParams).flatMap(workspaceService::delete);
	}
}
