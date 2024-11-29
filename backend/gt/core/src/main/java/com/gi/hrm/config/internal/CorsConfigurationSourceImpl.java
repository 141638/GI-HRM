package com.gi.hrm.config.internal;

import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Configuration
public class CorsConfigurationSourceImpl implements CorsConfigurationSource {
    /** CORS maxAge default value */
    private static final long DEFAULT_MAXAGE = 3600;
    /** CORS allowed origins variable */
    private final String[] allowedOrigins;
    /** CORS allowed methods variable */
    private final String[] allowedMethods;
    /** CORS allowed headers variable */
    private final String[] allowedHeaders;
    /** CORS maxAge variable */
    private final long maxAge;

    /**
     * Configuration constructor
     *
     * @param env interface representing the environment in .properties files
     */
    public CorsConfigurationSourceImpl(final Environment env) {
        allowedOrigins = env.getProperty("cors.allowed.origins", String[].class);
        allowedMethods = env.getProperty("cors.allowed.methods", String[].class);
        allowedHeaders = env.getProperty("cors.allowed.headers", String[].class);
        maxAge = env.getProperty("cors.max-age", Long.class, DEFAULT_MAXAGE);
    }

    /**
     * Custom cors configuration
     *
     * @return the configured cors
     */
    @Override
    public CorsConfiguration getCorsConfiguration(@NonNull final ServerWebExchange exchange) {
        final CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(List.of(allowedOrigins));
        cors.setAllowedMethods(List.of(allowedMethods));
        cors.setAllowedHeaders(List.of(allowedHeaders));
        cors.setMaxAge(maxAge);

        return cors;
    }
}
