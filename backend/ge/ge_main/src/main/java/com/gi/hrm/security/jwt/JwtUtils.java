package com.gi.hrm.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gi.hrm.context.ServiceTokenDto;
import com.gi.hrm.security.Authority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Slf4j
@Component
public class JwtUtils implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static boolean validate(String token, String serviceCd) {
        Authority auths = toServiceTokenDto(token).getAuthority();
        return auths != null && auths.getAuths().entrySet().stream().anyMatch(entry -> entry.getKey().equals(serviceCd));
    }

    public static DecodedJWT decode(String token) throws JWTDecodeException {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("token is null or empty");
        }
        return JWT.decode(token);
    }

    public static ServiceTokenDto toServiceTokenDto(String jwt) {
        ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(LocalDateTime.now());

        Assert.notNull(jwt, "jwt is null or empty");

        DecodedJWT decodedJWT = decode(jwt);
        String tokenId = decodedJWT.getId();
        String tokenIssuer = decodedJWT.getIssuer();
        String tokenSubject = decodedJWT.getSubject();
        Integer userId = decodedJWT.getClaim("user-id").asInt();
        String username = decodedJWT.getClaim("username").asString();
        String azp = decodedJWT.getClaim("azp").asString();
        LocalDateTime exp = LocalDateTime.ofEpochSecond(decodedJWT.getClaim("exp").asLong(), 0, zoneOffset);
        LocalDateTime authAt = LocalDateTime.ofEpochSecond(decodedJWT.getClaim("auth_at").asLong(), 0, zoneOffset);
        Claim authority = decodedJWT.getClaim("authority");

        String[] authorizationParts = azp.split("-");
        String serviceCd = authorizationParts[0];
        String serviceRoleCd = authorizationParts.length > 1 ? authorizationParts[1] : null;

        return ServiceTokenDto.builder()
                .tokenId(tokenId).tokenIssuer(tokenIssuer).tokenSubject(tokenSubject)
                .originalToken(jwt).userId(userId).username(username)
                .azp(azp).serviceCd(serviceCd).serviceRoleCd(serviceRoleCd)
                .exp(exp).authAt(authAt).authority(extractAuthorityFromClaim(authority))
                .build();
    }

    private static Authority extractAuthorityFromClaim(Claim authorityClaim) {
        if (!authorityClaim.isNull()) {
            return new Authority(authorityClaim);
        }

        return null;
    }
}
