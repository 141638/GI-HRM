package com.gi.hrm.config.internal.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JwtUtils implements Serializable {
    /** serial version UID */
    @Serial
    private static final long serialVersionUID = 1L;

    /** JWT private signing key */
    private final String jwtSigningKey;

    /** JWT expired time */
    private final Long jwtExpiredTime;

    /**
     * Constructor
     *
     * @param env environment property access
     */
    public JwtUtils(final Environment env) {
        this.jwtSigningKey = env.getProperty("com.gi.hrm.gt.jwtSecret");
        this.jwtExpiredTime = env.getProperty("com.gi.hrm.gt.jwtExpirationMs", Long.class);
    }

    /**
     * Generate JWT token using id, username and roles
     *
     * @param id user ID
     * @param username username
     * @param roles roles(authority)
     * @return a newly generated JWT token
     */
    public String generateJwtToken(final String id, final String username, final List<String> roles) {
        Date currentDate = new Date();
        List<String> auth = roles.stream().map(item -> "ROLE_" + item).toList();
        Map<String, Object> rolesClaim = new HashMap<>();
        rolesClaim.put("authority", Map.of("GE", Map.of("LD", auth)));
        rolesClaim.put("role", auth);
        rolesClaim.put("user-id", id);
        rolesClaim.put("azp", "GE-LD");
        rolesClaim.put("username", username);
        rolesClaim.put("auth_at", currentDate.getTime());
        Date expirationDate = new Date(System.currentTimeMillis() + jwtExpiredTime);

        JwtBuilder jwtToken = new DefaultJwtBuilder();
        jwtToken.setClaims(rolesClaim);
        jwtToken.setSubject(username);
        jwtToken.setIssuedAt(currentDate);
        jwtToken.setExpiration(expirationDate);
        jwtToken.signWith(SignatureAlgorithm.HS256, jwtSigningKey);

        return jwtToken.compact();
    }

    /**
     * Extract the username from a JWT token
     *
     * @param token JWT token
     * @return username
     */
    public String getUsername(final String token) {
        return Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Extract the expired time from a JWT token
     *
     * @param token JWT token
     * @return expired time
     */
    public Date getExpiredTimeAt(final String token) {
        return Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(token).getBody().getExpiration();
    }

    /**
     * Get all the claims from a JWT token
     *
     * @param token JWT token
     * @return JWT token's claims
     */
    public Claims getClaims(final String token) {
        return Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(token).getBody();
    }

    /**
     * Validate the JWT token
     *
     * @param token JWT token
     * @return true/false
     */
    public Boolean isValid(final String token) {
        try {
            return isExpired(token);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT Token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unable to validate JWT token: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Validate if the JWT token is expired
     *
     * @param token JWT token
     * @return true/false
     */
    public Boolean isExpired(final String token) {
        return this.getExpiredTimeAt(token).after(new Date());
    }
}
