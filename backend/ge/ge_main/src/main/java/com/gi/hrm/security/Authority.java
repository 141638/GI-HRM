package com.gi.hrm.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gi.hrm.security.jwt.JwtUtils;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Authority {
    private final Map<String, Map<String, List<String>>> auths;

    public Authority(Map<String, Map<String, List<String>>> auths) {
        Assert.notNull(auths, "auths is null or empty");
        this.auths = auths;
    }

    @SuppressWarnings("unchecked")
    public Authority(Claim claim) {
        Assert.notNull(claim, "claims is null or empty");
        this.auths = new HashMap<>();
        claim.asMap().forEach((key, value) -> {
            Map<String, List<String>> serviceRole = (Map<String, List<String>>) value;
            this.auths.put(key, serviceRole);
        });
    }

    @SuppressWarnings("unchecked")
    public Authority(String token) {
        DecodedJWT jwtProp = JwtUtils.decode(token);
        Claim claims = jwtProp.getClaim("authority");
        Assert.notNull(claims, "claims is null or empty");
        this.auths = new HashMap<>();
        claims.asMap().forEach((key, value) -> this.auths.put("GE", Map.of(key, (List<String>) value)));
    }

    public boolean hasAuth(String serviceCd, String serviceRoleCd, List<String> targetAuths) {
        Assert.notNull(serviceCd, "serviceCd is null or empty");
        Assert.notNull(serviceRoleCd, "serviceRoleCd is null or empty");
        Assert.notNull(targetAuths, "targetAuths is null or empty");

        Map<String, List<String>> authorizationMap = this.auths.get(serviceCd);
        if (authorizationMap == null) {
            return false;
        } else {
            List<String> authorizations = authorizationMap.get(serviceCd);
            if (authorizations == null || authorizations.isEmpty()) {
                return false;
            } else {
                return targetAuths.stream().anyMatch(authorizations::contains);
            }
        }
    }

    public Map<String, Map<String, List<String>>> getAuths() {
        return this.auths;
    }

    public Map<String, List<String>> getAuths(String serviceCd) {
        return this.auths.get(serviceCd);
    }

    public List<String> getAuths(String serviceCd, String serviceRoleCd) {
        var serviceRoleMap = this.auths.get(serviceCd);
        if (serviceRoleMap == null) {
            return Collections.emptyList();
        } else {
            return serviceRoleMap.get(serviceRoleCd);
        }
    }
}
