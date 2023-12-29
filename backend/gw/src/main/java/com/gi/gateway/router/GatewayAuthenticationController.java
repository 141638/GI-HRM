package com.gi.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gi.gateway.dto.request.login.LoginRequest;
import com.gi.gateway.dto.response.common.ApiResponse;
import com.gi.gateway.service.AuthenticationService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class GatewayAuthenticationController {
	private AuthenticationService authenticationService;

	@PostMapping("/login")
	public Mono<ResponseEntity<Mono<ApiResponse>>> loginSocket(@RequestBody LoginRequest request) {
		return Mono.just(ResponseEntity.ok().body(authenticationService.loginService(request)));
	}
}
