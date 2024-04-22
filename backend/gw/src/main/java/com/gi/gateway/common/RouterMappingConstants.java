package com.gi.gateway.common;

import java.util.regex.Pattern;

public class RouterMappingConstants {

    private RouterMappingConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final Pattern API_PATTERN = Pattern.compile("^(/\\w+)+(/)(\\*\\*)$");

    public static final String FROM_REWRITE_PATTERN = "**";
    public static final String TO_PREDICATE_REWRITE_PATTERN = "(?<segment>.*)";
    public static final String TO_URI_REWRITE_PATTERN = "${segment}";

    public static final String FALLBACK = "fallback";
    public static final String EMPLOYEE = "employee-route";
    public static final String TASKLOG_WORKSPACE = "tasklog.workspace-route";

    public static final String HTTP = "http://";
    public static final String LOAD_BALANCER = "lb://";

}
