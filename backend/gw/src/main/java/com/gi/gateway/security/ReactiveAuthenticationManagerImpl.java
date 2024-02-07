package com.gi.gateway.security;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.gi.gateway.exception.BadCredentialsException;
import com.gi.gateway.security.jwt.JwtUtils;
import com.gi.gateway.security.userDetails.ReactiveUserDetailsServiceImpl;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ReactiveAuthenticationManagerImpl implements ReactiveAuthenticationManager {
	private final JwtUtils jwtUtils;
	private final ReactiveUserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		String jwtToken = String.valueOf(authentication.getCredentials());
		Boolean jwtValid = jwtUtils.isValid(jwtToken);
		if (Objects.isNull(jwtValid) || Boolean.TRUE.equals(!jwtValid)) {
			return Mono.error(new BadCredentialsException("Token Invalid"));
		}
		Claims claims = jwtUtils.getClaims(jwtToken);
		String username = jwtUtils.getUsername(jwtToken);
		@SuppressWarnings("unchecked")
		Mono<List<String>> rolesMono = Mono.just((List<String>) claims.get("role", List.class))
		        .filter(roles -> roles != null && !roles.isEmpty())
		        .switchIfEmpty(Mono.error(new BadCredentialsException("roles not found")));

		return Mono.zip(rolesMono, userDetailsServiceImpl.findByUsername(username)).map(fluxZip -> {
			List<String> roleList = fluxZip.getT1();
			UserDetails userDetail = fluxZip.getT2();
			return new UsernamePasswordAuthenticationToken(userDetail, null,
			        roleList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		});
	}
}
