package com.gi.hrm.config.internal.user_details;

import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Authority {
    /** Authorization map */
    private final Map<String, Map<String, List<String>>> auths;

    /**
     * Constructor
     *
     * @param authsParam authorization map
     */
    public Authority(final Map<String, Map<String, List<String>>> authsParam) {
        Assert.notNull(authsParam, "auths is null or empty");
        this.auths = authsParam;
    }

    /**
     * Get full authorization map
     *
     * @return the authorization map
     */
    public Map<String, Map<String, List<String>>> getAuths() {
        return this.auths;
    }

    /**
     * Get authorization map by serviceCd
     *
     * @param serviceCd service business code
     * @return authorization map at serviceCd level
     */
    public Map<String, List<String>> getAuths(final String serviceCd) {
        return this.auths.get(serviceCd);
    }

    /**
     * Get authorization map by serviceCd and serviceRoleCd
     *
     * @param serviceCd service business code
     * @param serviceRoleCd service role business code
     * @return authorization map at serviceRoleCd level
     */
    public List<String> getAuths(final String serviceCd, final String serviceRoleCd) {
        var serviceRoleMap = this.auths.get(serviceCd);
        if (serviceRoleMap == null) {
            return Collections.emptyList();
        } else {
            return serviceRoleMap.get(serviceRoleCd);
        }
    }
}
