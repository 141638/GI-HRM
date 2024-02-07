package com.gi.gateway.security;

public class AuthConfigConstants {
	// API End-points Configuration
	protected static final String[] OPEN_API_ENDPOINTS = { "/api/auth/login", "/api/auth/register",
	        "/api/auth/reset-password" };
	protected static final String[] AUTH_WHITELIST = {
	        // -- Swagger UI v2
	        "/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
	        "/configuration/security", "/swagger-ui.html", "/webjars/**",
	        // -- Swagger UI v3 (OpenAPI)
	        "/v3/api-docs/**", "/swagger-ui/**" };
	protected static final String[] SSE_API = {};
	protected static final String[] SYSTEM_STAFF_API = { "/api/resource/employee/**" };

	// CORS Configuration
	public static final String[] CORS_ALLOWED_ORIGINS = { "http://localhost:4201" };
	public static final String[] CORS_ALLOWED_METHODS = { "OPTIONS", "GET", "POST", "PUT", "DELETE" };
	public static final long CORS_MAX_AGE = 3600L;

	// Common Constants
	protected static final String AUTH_TOKEN_TYPE = "Bearer";
}
