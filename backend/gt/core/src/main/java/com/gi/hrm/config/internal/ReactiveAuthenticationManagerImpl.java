package com.gi.hrm.config.internal;

import com.gi.hrm.config.internal.jwt.JwtUtils;
import com.gi.hrm.config.internal.user_details.ReactiveUserDetailsServiceImpl;
import com.gi.hrm.config.internal.user_details.UserDetailsImpl;
import com.gi.hrm.exception.BadCredentialsException;
import com.gi.hrm.utils.ValidationUtils;
import com.gi.hrm.utils.bean.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Objects;

import static com.gi.hrm.config.cache.CacheManagerConfig.INVALID_JWT_CACHE;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReactiveAuthenticationManagerImpl implements ReactiveAuthenticationManager {
    /** JWT utility class */
    private final JwtUtils jwtUtils;
    /** User details service */
    private final ReactiveUserDetailsServiceImpl userDetailsServiceImpl;
    /** Utility class for cache management */
    private final CacheService cacheService;

    /**
     * Authenticate user based on the provided request's authentication credentials
     *
     * @param authentication the {@link Authentication} to test
     * @return Authentication
     */
    @Override
    public Mono<Authentication> authenticate(final Authentication authentication) {
        String jwtToken = String.valueOf(authentication.getCredentials());
        Boolean jwtValid = jwtUtils.isValid(jwtToken);

        if (Objects.isNull(jwtValid) || Boolean.TRUE.equals(!jwtValid) || checkTokenInBlacklist(jwtToken)) {
            return Mono.error(new BadCredentialsException("Token Invalid"));
        }

        return Mono.just(new UsernamePasswordAuthenticationToken(
                UserDetailsImpl.build(jwtUtils.getUsername(jwtToken)),
                null,
                Collections.emptyList()));
    }

    /**
     * Check if the token is in blacklist or not
     *
     * @param token jwt token
     * @return true/false
     */
    private boolean checkTokenInBlacklist(String token) {
        if (ValidationUtils.nonNullOrEmpty(cacheService.cacheGet(INVALID_JWT_CACHE, token))) {
            log.error("JWT token is blacklisted");
            return true;
        }
        return false;
    }
}
