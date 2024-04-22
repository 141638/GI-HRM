package com.gi.hrm.context;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class RequestInfo {
    private ServiceTokenDto serviceTokenDto;

    public RequestInfo() {
    }

    public ServiceTokenDto getServiceTokenDto() {
        return this.serviceTokenDto;
    }

    public void setServiceTokenDto(ServiceTokenDto serviceTokenDto) {
        this.serviceTokenDto = serviceTokenDto;
    }
}
