package com.gi.hrm.config.cache.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.NonNegative;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class QueryResultCaffeineConfig {
    /** query result caffeine bean name */
    public static final String QUERY_CAFFEINE_BEAN = "queryResultCaffeineBean";

    /** Initial capacity for the query cache */
    private final int cacheInitialCapacity;
    /** Maximum size for the query cache */
    private final int cacheMaximumSize;
    /** Expired time for the query */
    private final long expiredAfterWrite;

    /**
     * Constructor
     *
     * @param env environment property access
     */
    public QueryResultCaffeineConfig(final Environment env) throws NullPointerException {
        this.cacheInitialCapacity = Optional
                .ofNullable(env.getProperty("cache.caffeine.queryResult.initialCapacity", Integer.class))
                .orElseThrow(() -> new IllegalArgumentException("cache.caffeine.queryResult.initialCapacity"));
        this.cacheMaximumSize = Optional
                .ofNullable(env.getProperty("cache.caffeine.queryResult.maximumSize", Integer.class))
                .orElseThrow(() -> new IllegalArgumentException("cache.caffeine.queryResult.maximumSize"));
        this.expiredAfterWrite = Optional
                .ofNullable(env.getProperty("cache.caffeine.queryResult.expiredAfterWrite", Integer.class))
                .orElseThrow(() -> new IllegalArgumentException("cache.caffeine.queryResult.expiredAfterWrite"));
    }

    /**
     * Initialize bean for query result
     *
     * @return query result caffeine
     */
    @Bean(QUERY_CAFFEINE_BEAN)
    public Caffeine<Object, Object> mongoQueryCaffeine() {
        return Caffeine
                .newBuilder()
                .recordStats()
                .initialCapacity(cacheInitialCapacity)
                .maximumSize(cacheMaximumSize)
                .expireAfter(new Expiry<>() {
                    @Override
                    public long expireAfterCreate(Object any, Object email, long cd) {
                        return TimeUnit.SECONDS.toNanos(expiredAfterWrite);
                    }

                    @Override
                    public long expireAfterUpdate(
                            Object any, Object email, long ct, @NonNegative long cd
                    ) {
                        return cd;
                    }

                    @Override
                    public long expireAfterRead(Object s, Object l, long ct, @NonNegative long cd) {
                        return cd;
                    }
                });
    }

}
