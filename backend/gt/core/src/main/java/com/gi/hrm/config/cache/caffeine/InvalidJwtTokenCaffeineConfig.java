package com.gi.hrm.config.cache.caffeine;

import com.gi.hrm.config.internal.jwt.JwtUtils;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.NonNegative;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class InvalidJwtTokenCaffeineConfig {
    /** Invalid JWT token caffeine bean name */
    public static final String INVALID_JWT_CAFFEINE_BEAN = "invalidJwtTokenCaffeineBean";

    /** Utility class of jwt token */
    private final JwtUtils jwtUtils;

    /** Initial capacity for the JWT cache */
    private final int jwtCacheInitialCapacity;
    /** Maximum size for the JWT cache */
    private final int jwtCacheMaximumSize;
    /** Default expireAfterTime(ms) in case can not calculate the exact expired time of the JWT token */
    private final long jwtNotRetrieveExpiredDefaultAfterWrite;


    /**
     * Constructor
     *
     * @param jwtUtils Utility class of jwt token
     * @param env environment property access
     */
    public InvalidJwtTokenCaffeineConfig(final JwtUtils jwtUtils, final Environment env) {
        this.jwtUtils = jwtUtils;
        this.jwtCacheInitialCapacity = Optional
                .ofNullable(env.getProperty("cache.caffeine.jwt.initialCapacity", Integer.class))
                .orElseThrow(() -> new IllegalArgumentException("cache.caffeine.jwt.initialCapacity"));
        this.jwtCacheMaximumSize = Optional
                .ofNullable(env.getProperty("cache.caffeine.jwt.maximumSize", Integer.class))
                .orElseThrow(() -> new IllegalArgumentException("cache.caffeine.jwt.maximumSize"));
        this.jwtNotRetrieveExpiredDefaultAfterWrite = Optional
                .ofNullable(env.getProperty("cache.caffeine.jwt.notRetrieveExpiredDefaultAfterWrite", Integer.class))
                .orElseThrow(
                        () -> new IllegalArgumentException("cache.caffeine.jwt.notRetrieveExpiredDefaultAfterWrite"));
    }

    /**
     * Initialize bean for invalidated jwt token caffeine
     *
     * @return jwt token caffeine
     */
    @Bean(INVALID_JWT_CAFFEINE_BEAN)
    public Caffeine<Object, Object> invalidJwtTokenCaffeine() {
        return Caffeine.newBuilder()
                .recordStats()
                .initialCapacity(jwtCacheInitialCapacity)
                .maximumSize(jwtCacheMaximumSize)
                .expireAfter(new Expiry<>() {

                    @Override
                    public long expireAfterCreate(Object jwtToken, Object email, long cd) {
                        try {
                            return TimeUnit.MILLISECONDS.toNanos(
                                    jwtUtils.getExpiredTimeAt(String.valueOf(jwtToken))
                                            .getTime() - new Date().getTime());
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                            return TimeUnit.SECONDS.toNanos(jwtNotRetrieveExpiredDefaultAfterWrite);
                        }
                    }

                    @Override
                    public long expireAfterUpdate(
                            Object jwtToken, Object email, long currentTime, @NonNegative long cd
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
