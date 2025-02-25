package com.gi.hrm.utils;

import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class IterationUtils {
    /**
     * Get item from array avoiding IndexOutOfBoundException/NullPointerException
     *
     * @param items an array of items
     * @param index index to get item
     * @param <T> generic type of item
     * @return null/item
     */
    public static <T> T get(T[] items, int index) {
        if (index >= items.length) {
            return null;
        } else {
            return items[index];
        }
    }

    /**
     * Group a list into a map
     *
     * @param list list to be grouped
     * @param keyGetter function ref to get map key inside the list item
     * @param <T> generic type for the list item
     * @param <S> genetic type for the key
     * @return a map grouped using the list and the keyGetter
     */
    public static <T, S> Map<S, List<T>> group(List<T> list, Function<? super T, S> keyGetter) {
        if (ValidationUtils.isNullOrEmpty(list) || ValidationUtils.isNullOrEmpty(keyGetter)) {
            return Collections.emptyMap();
        } else {
            return group(list.stream(), keyGetter);
        }
    }

    /**
     * Group a stream into a map
     *
     * @param stream stream to be grouped
     * @param keyGetter function ref to get map key inside the list item
     * @param <T> generic type for the list item
     * @param <S> genetic type for the key
     * @return a map grouped using the list and the keyGetter
     */
    public static <T, S> Map<S, List<T>> group(Stream<T> stream, Function<? super T, S> keyGetter) {
        if (ValidationUtils.isNullOrEmpty(stream) || ValidationUtils.isNullOrEmpty(keyGetter)) {
            return Collections.emptyMap();
        } else {
            return stream.collect(Collectors.groupingBy(keyGetter));
        }
    }
}
