package com.gi.hrm.command.domain;

public abstract class AbstractAggregateRoot<I extends ValueObject, A extends AggregateRoot<I, A>> implements AggregateRoot<I, A> {

    @Override
    public A persist() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
