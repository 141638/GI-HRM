package com.gi.hrm.task.application.category;

import com.gi.hrm.presentation.PreBuiltServerResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class CategoryServiceImpl {
    private final CategoryDetailService detailService;

    public Mono<ServerResponse> upsert(ServerRequest request) {
//        return request.bodyToMono(CategoryUpserRequest.class).map(detailService::upsertCategory)
//                .flatMap(PreBuiltServerResponse::success);
        return Mono.empty();
    }

    public Mono<ServerResponse> list(ServerRequest request) {
//        return PreBuiltServerResponse.success(detailService.listCategory());
        return Mono.empty();
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
//        return Mono.just(request.queryParam("value")).flatMap(optional -> optional.map(Mono::just).orElseGet(Mono::empty))
//                .map(detailService::deleteCategory).flatMap(PreBuiltServerResponse::success);
        return Mono.empty();
    }

    public Mono<ServerResponse> dropdownCategoryByWorkspaceId(ServerRequest request) {
        return PreBuiltServerResponse
                .success(Flux.fromStream(Stream.iterate(1, x -> x + 1)).take(10).delayElements(Duration.ofSeconds(1)));
    }

    public Mono<ServerResponse> fluxStreamTest(ServerRequest request) {
        return PreBuiltServerResponse
                .success(Flux.fromStream(Stream.iterate(1, x -> x + 1)).delayElements(Duration.ofMillis(300)));
    }
}
