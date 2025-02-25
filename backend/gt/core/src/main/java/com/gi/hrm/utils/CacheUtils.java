package com.gi.hrm.utils;

import lombok.experimental.UtilityClass;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

@UtilityClass
public class CacheUtils {

    /**
     * Comparator used to compare 2 values of string, number first, then character
     *
     * @param v1 value 1 to be compared
     * @param v2 value 2 to be compared
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater
     *         than the second.
     */
    public static int comparator(String v1, String v2) {
        return Comparator.comparing(CacheUtils::extractNumber)
                .thenComparing(CacheUtils::extractPrefix).compare(v1, v2);
    }

    /**
     * Extract the alphabetic prefix from the string.
     * If the string starts with numbers, return an empty string.
     *
     * @param str the input string
     * @return the alphabetic prefix (case-insensitive)
     */
    private static String extractPrefix(String str) {
        return str.replaceAll("[^a-zA-Z]+.*", "").toLowerCase(Locale.ENGLISH);
    }

    /**
     * Extract the numeric part from the string.
     * If no number is found, return 0.
     *
     * @param str the input string
     * @return the numeric part of the string
     */
    private static int extractNumber(String str) {
        String numericPart = str.replaceAll("^\\D*", "").replaceAll("\\D+.*", "");
        return numericPart.isEmpty() ? 0 : Integer.parseInt(numericPart);
    }

    /**
     * Build cache key, each key component is separated by symbol `&&`. After building complete, the complete cacheKey
     * will be sanitized before returning
     *
     * @param keyComponents key components to build into the cache key
     * @return sanitized built cache key
     */
    public static String cacheKeyBuilder(String... keyComponents) {
        if (ValidationUtils.isNullOrEmpty(keyComponents)) {
            throw new IllegalArgumentException("keyComponents cannot be empty for this action");
        } else {
            final StringBuilder keyBuilder = new StringBuilder();
            Arrays.stream(keyComponents).forEach(keyComp -> keyBuilder.append("::").append(keyComp));
            return URLEncoder.encode(keyBuilder.toString(), StandardCharsets.UTF_8);
        }
    }
}
