package com.gi.gateway.router.handlerimpl;

import com.gi.gateway.common.ReactiveCommonService;
import com.gi.gateway.common.RouterMappingConstants;
import com.gi.gateway.common.Utils;
import com.gi.gateway.dto.request.tasklog.WorkspaceUpdateRequest;
import com.gi.gateway.dto.response.common.ApiResponse;
import com.gi.gateway.dto.response.common.PreBuiltReactiveServerResponse;
import com.gi.gateway.router.handler.tasklog.TasklogWorkspaceRouterHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class TasklogWorkspaceRouterHandlerImpl implements TasklogWorkspaceRouterHandler {
    private final WebClient webClient;
    private final ReactiveCommonService reactService;

//    private static final String ENDPOINT = String
//            .valueOf(Utils.getJsonEndpoints().get(RouterMappingConstants.TASKLOG) + "/api/workspace");

//    @Override
//    public Mono<ServerResponse> search(ServerRequest request) {
//        String uri = reactService.buildUri(ENDPOINT, "search", request.queryParams());
//        Flux<ApiResponse> searchResponseFlux = webClient.get().uri(uri).retrieve().bodyToFlux(ApiResponse.class).log();
//        return PreBuiltReactiveServerResponse.successSSE(searchResponseFlux);
//    }
//
//    @Override
//    public Mono<ServerResponse> upsert(ServerRequest request) {
//        String uri = reactService.buildUri(ENDPOINT, "upsert");
//        return request.bodyToMono(WorkspaceUpdateRequest.class).map(requestBody ->
//                webClient.put().uri(uri).body(Mono.just(requestBody), WorkspaceUpdateRequest.class).retrieve().bodyToMono(ApiResponse.class).log()
//        ).flatMap(PreBuiltReactiveServerResponse::success);
//    }
}
