package com.gi.hrm.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@UtilityClass
public class CommonUtils {
    /**
     * Get coalesce between 2 value
     *
     * @param checkNullValue (a): value to perform null check
     * @param alternativeValue (b): value if the first one is null
     * @param <T> generic type for both values
     * @return not-null (a), or (b)
     */
    public static <T> T coalesce(T checkNullValue, T alternativeValue) {
        return Objects.isNull(checkNullValue) ? alternativeValue : checkNullValue;
    }

    /**
     * Check if an item is inside the provided array or not
     *
     * @param items array of items to be checked
     * @param itemToCheck item used to check the array
     * @param <T> generic type for array and item
     * @return true/false
     */
    public static <T> Boolean arrayContains(T[] items, T itemToCheck) {
        for (T item : items) {
            if (item.equals(itemToCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Call object's toString function with null-safe
     *
     * @param obj object to use toString
     * @param alterString default value if the obj is null
     * @return object in string
     */
    public static String toStringCoalesce(Object obj, String alterString) {
        return Objects.nonNull(obj) ? String.valueOf(obj) : alterString;
    }

    /**
     * Null-safely retrieving a single param result from multi-value map as int type
     *
     * @param paramMap multi-value map
     * @param name param name
     * @return retrieved param result
     */
    public static Integer retrieveSingleInt(MultiValueMap<String, String> paramMap, String name) {
        List<String> params = paramMap.get(name);

        return ValidationUtils.nonNullOrEmpty(params) ? NumberUtils.parseInt(params.getFirst()) : null;
    }

    /**
     * Null-safely retrieving a single param result from multi-value map as string
     *
     * @param paramMap multi-value map
     * @param name param name
     * @return retrieved param result as string, or null if it does not exist
     */
    public static String retrieveSingleParam(MultiValueMap<String, String> paramMap, String name) {
        List<String> params = paramMap.get(name);
        return ValidationUtils.nonNullOrEmpty(params) ? params.getFirst() : null;
    }

    /**
     * Null-safely retrieving a single param result as string from multi-value map as string, throw 400 if null
     *
     * @param paramMap multi-value map
     * @param name param name
     * @param errorMessage message to be thrown when the param does not exist
     * @return retrieved param result as string, or null if it does not exist
     */
    public static String retrieveSingleString(MultiValueMap<String, String> paramMap,
                                              String name, String errorMessage) {
        List<String> params = paramMap.get(name);
        if (ValidationUtils.nonNullOrEmpty(params)) {
            return params.getFirst();
        } else {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Null-safely retrieving a single param result as boolean from multi-value map as string, throw 400 if null
     *
     * @param paramMap multi-value map
     * @param name param name
     * @param errorMessage message to be thrown when the param does not exist
     * @return retrieved param result as boolean, or null if it does not exist
     */
    public static Boolean retrieveSingleBoolean(MultiValueMap<String, String> paramMap,
                                                String name, String errorMessage) {
        List<String> params = paramMap.get(name);
        if (ValidationUtils.nonNullOrEmpty(params)) {
            return Boolean.parseBoolean(params.getFirst());
        } else {
            if (ValidationUtils.nonNullOrEmpty(errorMessage)) {
                throw new IllegalArgumentException(errorMessage);
            } else {
                return false;
            }
        }
    }

    /**
     * Null-safely retrieving a single param result from multi-value map as string, throw 400 if null
     *
     * @param paramMap multi-value map
     * @param name param name
     * @param errorMessage message to be thrown when the param does not exist
     * @return retrieved param result as string, or null if it does not exist
     */
    public static List<String> retrieveParamAsListString(MultiValueMap<String, String> paramMap,
                                                         String name, String errorMessage) {
        String param = retrieveSingleString(paramMap, name, errorMessage);
        return StringUtils.stringToList(param, String.class);
    }

    /**
     * Null-safely retrieving a single param result from multi-value map as string, does not throw exception
     *
     * @param paramMap multi-value map
     * @param name param name
     * @return retrieved param result as string, or null if it does not exist
     */
    public static List<String> retrieveParamAsListString(MultiValueMap<String, String> paramMap, String name) {
        List<String> params = paramMap.get(name);
        if (ValidationUtils.nonNullOrEmpty(params)) {
            return StringUtils.stringToList(params.getFirst(), String.class);
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Null-safely perform a function. If the function param is null, then return null, else, apply the function
     *
     * @param value function param
     * @param func function reference
     * @param <T> generic type for function param
     * @param <S> generic type for function result
     * @return function result
     */
    public static <T, S> S execNS(T value, Function<T, S> func) {
        if (ValidationUtils.isNullOrEmpty(value)) {
            return null;
        } else {
            return func.apply(value);
        }
    }
}
