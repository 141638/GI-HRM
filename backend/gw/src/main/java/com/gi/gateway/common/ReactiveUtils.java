package com.gi.gateway.common;

import java.util.Optional;

import reactor.core.publisher.Mono;

public class ReactiveUtils {
	private ReactiveUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static <T> Mono<T> optionalToMono(Optional<T> opt) {
		return Mono.fromCallable(opt::get).switchIfEmpty(Mono.empty());
	}
}
