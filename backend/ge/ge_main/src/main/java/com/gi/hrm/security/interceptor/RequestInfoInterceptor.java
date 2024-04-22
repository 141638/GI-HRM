package com.gi.hrm.security.interceptor;

import com.gi.hrm.context.RequestInfo;
import com.gi.hrm.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;

public class RequestInfoInterceptor implements HandlerInterceptor {
    private RequestInfo requestInfo;

    @Autowired
    public RequestInfoInterceptor(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    public static final String REQ_ATTR_SERVICE_TOKEN = "reqAttributeServiceTokenForRequestInterceptor";

    public RequestInfoInterceptor() {
        // NoArgsConstructor
    }

    public boolean preHanlde(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Assert.notNull(request, "request is null or empty");
        Assert.notNull(response, "response is null or empty");
        Assert.notNull(handler, "handler is null or empty");

        String token = String.valueOf(request.getAttribute(REQ_ATTR_SERVICE_TOKEN));
        if (token != null) {
            this.requestInfo.setServiceTokenDto(JwtUtils.toServiceTokenDto(token));
        }

        return true;
    }
}
