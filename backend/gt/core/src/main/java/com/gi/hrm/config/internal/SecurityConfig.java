package com.gi.hrm.config.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.FormLoginSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpBasicSpec;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    /** public APIs */
    private final String[] permitAPIs;
    /** Class configuration handles authentication */
    private final ReactiveAuthenticationManagerImpl authenticationManager;
    /** Class configuration handles set security context */
    private final SecurityContextRepositoryImpl securityContextRepository;
    /** Class configuration customizes CORS */
    private final CorsConfigurationSourceImpl corsConfig;

    /**
     * Constructor
     *
     * @param env environment property access
     * @param authenticationManagerBean configured bean of authenticationManager
     * @param securityContextRepositoryBean configured bean of securityContextRepository
     * @param corsConfigBean configured bean of corsConfig
     */
    @Autowired
    public SecurityConfig(final Environment env,
                          final ReactiveAuthenticationManagerImpl authenticationManagerBean,
                          final SecurityContextRepositoryImpl securityContextRepositoryBean,
                          final CorsConfigurationSourceImpl corsConfigBean) {
        this.permitAPIs = env.getProperty("security.api.allowed", String[].class);
        this.authenticationManager = authenticationManagerBean;
        this.securityContextRepository = securityContextRepositoryBean;
        this.corsConfig = corsConfigBean;
    }

    /**
     * Define BCrypt encoder bean
     *
     * @return new BCryptPasswordEncoder
     */
    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Config HTTP security web filter chain
     *
     * @param http http request configurations provider
     * @return built SecurityWebFilterChain
     */
    @Bean
    SecurityWebFilterChain filterChain(final ServerHttpSecurity http) {
        http.cors(t -> t.configurationSource(corsConfig));
        http.csrf(CsrfSpec::disable);
        http.formLogin(FormLoginSpec::disable);
        http.httpBasic(HttpBasicSpec::disable);
        http.authenticationManager(authenticationManager);
        http.securityContextRepository(securityContextRepository);
        http.authorizeExchange(authorize -> authorize
                .pathMatchers(permitAPIs).permitAll()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .anyExchange().authenticated());
        return http.build();
    }
}
