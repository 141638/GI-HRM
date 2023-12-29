package com.gi.gateway.security.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils implements Serializable {
	private static final long serialVersionUID = 1L;

	@Value("${hrm.jwt.secret}")
	private String jwtSigningKey;

	@Value("${hrm.jwt.expiredTime}")
	private Long jwtExpiredTime;

	public String generateJwtToken(String username, List<String> roles) {
		roles = roles.stream().map(item -> "ROLE_" + item).collect(Collectors.toList());
		Map<String, Object> rolesClaim = new HashMap<>(Map.of("role", roles));
		Date currentDate = new Date();
		Date expirationDate = new Date(System.currentTimeMillis() + jwtExpiredTime);

		JwtBuilder jwtToken = new DefaultJwtBuilder();
		jwtToken.setClaims(rolesClaim);
		jwtToken.setSubject(username);
		jwtToken.setIssuedAt(currentDate);
		jwtToken.setExpiration(expirationDate);
		jwtToken.signWith(SignatureAlgorithm.HS256, jwtSigningKey);

		return jwtToken.compact();
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(token).getBody().getSubject();
	}

	public Date getExpiredTimeAt(String token) {
		return Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(token).getBody().getExpiration();
	}

	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(token).getBody();
	}

	public Boolean isValid(String token) {
		try {
			isExpired(token);
			validateTokenIsNotForALoggedOutDevice(token);
			return true;
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT Token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		} catch (Exception e) {
			log.error("Invalid JWT Token: {}", e.getMessage());
		}
		return false;
	}

	public Boolean isExpired(String token) {
		return this.getExpiredTimeAt(token).before(new Date());
	}

	private void validateTokenIsNotForALoggedOutDevice(String jwtToken) {
	}
}
