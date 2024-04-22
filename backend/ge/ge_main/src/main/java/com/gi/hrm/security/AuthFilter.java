package com.gi.hrm.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.gi.hrm.security.interceptor.RequestInfoInterceptor;
import com.gi.hrm.security.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.filter.RequestContextFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class AuthFilter extends RequestContextFilter {
    @Value("${com.gi.hrm.service-cd}")
    private String serviceCd;

    public AuthFilter() {
        // NoArgsConstructor
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Assert.notNull(request, "request is null or empty");
        Assert.notNull(response, "response is null or empty");
        Assert.notNull(filterChain, "filterChain is null or empty");

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        token = token == null ? null : token.replaceFirst("Bearer ", "");

        if (token == null) {
            serverErrorResponse(response, HttpStatus.UNAUTHORIZED.value(), "token not found");
        } else {
            if (this.validate(request, token)) {
                request.setAttribute(RequestInfoInterceptor.REQ_ATTR_SERVICE_TOKEN, token);
                filterChain.doFilter(request, response);
            } else {
                serverErrorResponse(response, HttpStatus.FORBIDDEN.value(), "token invalid");
            }
        }
    }

    private void serverErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(message);
    }

    private boolean validate(HttpServletRequest request, String token) {
        if (token == null || token.isEmpty()) {
            return false;
        } else {
            boolean hasInternalAuthorization;
            try {
                hasInternalAuthorization = JwtUtils.validate(token, this.serviceCd);
            } catch (JWTDecodeException | IllegalArgumentException e) {
                return false;
            }
            boolean isReferenceApi = request.getRequestURI().startsWith(request.getContextPath() + "/api");

            return hasInternalAuthorization || isReferenceApi;
        }
    }
}
