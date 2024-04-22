package com.gi.gateway.security.user_details;

import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Authority {
    private final Map<String, Map<String, List<String>>> auths;

    public Authority(Map<String, Map<String, List<String>>> auths) {
        Assert.notNull(auths, "auths is null or empty");
        this.auths = auths;
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
