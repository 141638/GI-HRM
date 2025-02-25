package com.gi.hrm.command.domain;

public interface AggregateRoot<I extends ValueObject, A extends AggregateRoot<I, A>> {
    I getId();

    EntityState getAggregateState();

    A persist();
}
