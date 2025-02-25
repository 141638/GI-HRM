package com.gi.hrm.command.domain;

public interface Entity<I extends ValueObject, E extends Entity<I, E>> {
    I getId();

    EntityState getEntityState();

    E persistIfDirty();
}
