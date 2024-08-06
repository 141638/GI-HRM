package com.gi.gateway.config;

import com.gi.gateway.common.RouterMappingConstants;
import com.gi.gateway.dto.RouterProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.gi.gateway.common.RouterMappingConstants.API_PATTERN;
import static com.gi.gateway.common.RouterMappingConstants.FROM_REWRITE_PATTERN;
import static com.gi.gateway.common.RouterMappingConstants.TO_PREDICATE_REWRITE_PATTERN;
import static com.gi.gateway.common.RouterMappingConstants.TO_URI_REWRITE_PATTERN;

@Configuration
@RequiredArgsConstructor
public class RouteConfig {
    private final Map<String, RouterProperties> routerMappingProperties;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        var routerBuilder = routeLocatorBuilder.routes();
        routerMappingProperties.forEach((s, router) ->
            routerBuilder.route(router.getHost(), p -> p
                .path(router.getPattern())
                .filters(f -> {
                    rewritePath(f, router.getPattern(), router.getUri());
                    return f;
                })
                .uri(checkHostLoadBalancingApplying(router))));
        return routerBuilder.build();
    }

    private String checkHostLoadBalancingApplying(RouterProperties router) {
        String hostURI = router.getHost();
        if (Boolean.TRUE.equals(router.getIsLoadBalanced())) {
            return hostURI.replace(RouterMappingConstants.HTTP, RouterMappingConstants.LOAD_BALANCER);
        }
        return hostURI;
    }

    /**
     * the function to rewrite the predicate to the endpoint uri path
     *
     * @param f         filter spec
     * @param predicate the request pattern received
     * @param uri       the route destination uri
     */
    private void rewritePath(GatewayFilterSpec f, String predicate, String uri) {
        // valid predicate pattern
        if (!API_PATTERN.matcher(predicate).matches()) {
            throw new IllegalArgumentException("predicate is not valid");
        }

        // valid uri pattern
        if (!API_PATTERN.matcher(uri).matches()) {
            throw new IllegalArgumentException("uri is not valid");
        }

        f.rewritePath(predicate.replace(FROM_REWRITE_PATTERN, TO_PREDICATE_REWRITE_PATTERN),
            uri.replace(FROM_REWRITE_PATTERN, TO_URI_REWRITE_PATTERN));
    }
}
