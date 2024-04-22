package com.gi.gateway.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouterValidator {
    private final List<String> publicApiEndpoints;

    @Autowired
    public RouterValidator(List<String> publicApiEndpoints) {
        this.publicApiEndpoints = publicApiEndpoints;
    }

    public Boolean isSecured(ServerHttpRequest request) {
        return publicApiEndpoints.stream().noneMatch(uri -> request.getURI().getPath().equals(uri));
    }
}
