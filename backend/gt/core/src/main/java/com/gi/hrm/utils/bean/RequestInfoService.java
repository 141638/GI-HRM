package com.gi.hrm.utils.bean;

import com.gi.hrm.exception.BadCredentialsException;
import com.gi.hrm.config.internal.user_details.UserDetailsImpl;
import com.gi.hrm.utils.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Service class to handle SecurityContextHolder/ Request information
 */
@Service
public class RequestInfoService {
    /**
     * Get current login system user detail
     *
     * @return current system user detail
     */
    public Mono<UserDetailsImpl> getUserDetailsMono() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .switchIfEmpty(Mono.error(new BadCredentialsException("principal not found")))
                .map(ObjectUtils::cast);
    }
}
