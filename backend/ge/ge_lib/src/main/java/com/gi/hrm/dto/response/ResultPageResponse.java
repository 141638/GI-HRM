package com.gi.hrm.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
