package com.gi.gateway.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@PreAuthorize("hasRole('STAFF')")
public interface EmployeeGatewayService {
	Mono<ServerResponse> searchEmployee(ServerRequest request);

	Mono<ServerResponse> addEmployee(ServerRequest request);

	Mono<ServerResponse> detailsEmployee(ServerRequest request);

	Mono<ServerResponse> deleteEmployee(ServerRequest request);

}
