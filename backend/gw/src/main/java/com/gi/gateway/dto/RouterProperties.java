package com.gi.gateway.dto;

import lombok.Data;

@Data
public class RouterProperties {
    private String pattern;
    private String host;
    private String uri;
    private Boolean isLoadBalanced;

    public RouterProperties(Object pattern, Object host, Object uri, Object isLoadBalanced) {
        this.pattern = String.valueOf(pattern);
        this.host = String.valueOf(host);
        this.uri = String.valueOf(uri);
        this.isLoadBalanced = Boolean.parseBoolean(String.valueOf(isLoadBalanced));
    }
}
