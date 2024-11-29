package com.gi.hrm.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CoreConstants {
    /** Prefix pattern for bearer token */
    public static final String BEARER_PREFIX = "Bearer ";

    /** Message constants for exception when caching failed */
    public static final String FETCH_TO_CACHE_FAILED = "Failed to fetch and cache query results for cacheKey: {}";
}
