package com.gi.hrm.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.gi.hrm.config.cache.caffeine.InvalidJwtTokenCaffeineConfig.INVALID_JWT_CAFFEINE_BEAN;
import static com.gi.hrm.config.cache.caffeine.QueryResultCaffeineConfig.QUERY_CAFFEINE_BEAN;


@Slf4j
@Configuration
@EnableCaching
public class CacheManagerConfig {
    /** Invalid JWT token cache name */
    public static final String INVALID_JWT_CACHE = "invalidJwtTokenCache";
    /** Query result cache name */
    public static final String QUERY_CACHE = "queryCache";
    /** Caffeine cache manager bean name */
    public static final String CAFFEINE_CACHE_MANAGER_BEAN = "caffeineCacheManager";

    /**
     * Setup Spring's cache manager
     *
     * @param jwtCaffeine caffeine of invalidated JWT token
     * @param queryResultCaffeine caffeine of a query result
     * @return caffeineCacheManager
     */
    @Bean(CAFFEINE_CACHE_MANAGER_BEAN)
    public CacheManager cacheManager(
            @Qualifier(INVALID_JWT_CAFFEINE_BEAN) Caffeine<Object, Object> jwtCaffeine,
            @Qualifier(QUERY_CAFFEINE_BEAN) Caffeine<Object, Object> queryResultCaffeine) {

        // setup caffeine cache manager
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setAsyncCacheMode(true);
        caffeineCacheManager.registerCustomCache(INVALID_JWT_CACHE, jwtCaffeine.buildAsync());
        caffeineCacheManager.registerCustomCache(QUERY_CACHE, queryResultCaffeine.buildAsync());

        return caffeineCacheManager;
    }
}
