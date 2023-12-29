package com.gi.gateway.dto.request.common;

import lombok.Getter;

@Getter
public class CommonPaginatorRequest {
    private Integer pageSize = 12;
    private Integer currentPage = 1;
}
