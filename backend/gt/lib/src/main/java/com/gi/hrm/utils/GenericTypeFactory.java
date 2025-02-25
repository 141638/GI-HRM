package com.gi.hrm.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

/**
 * Factory class for instantiating an object based on a generic type <T>
 *
 * @param <T> generic type
 */
@Slf4j
public class GenericTypeFactory<T> {
    /** generic type */
    private final Class<T> type;

    /**
     * Constructor
     *
     * @param type type of the object
     */
    public GenericTypeFactory(final Class<T> type) {
        this.type = type;
    }

    /**
     * Create new instance from the object type
     *
     * @return new object
     */
    public T createInstance() {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException
                 | NoSuchMethodException e) {
            log.error("Error instantiating a new object using generic type", e);
            return null;
        }
    }
}
