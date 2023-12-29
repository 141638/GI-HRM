package com.gi.gateway.dto.response.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultPageResponse {
    private Integer totalItems;
    private List<?> items;
    private Integer totalPages;
    private Integer currentPage;
}
