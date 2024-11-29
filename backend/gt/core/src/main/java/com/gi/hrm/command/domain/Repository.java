package com.gi.hrm.command.domain;

import reactor.core.publisher.Mono;

public interface Repository<I extends ValueObject, A extends AggregateRoot<I, A>> {
    Mono<A> save(A aggregateRoot);

    Mono<A> findById(I id);

    Mono<A> get(I id);
}
