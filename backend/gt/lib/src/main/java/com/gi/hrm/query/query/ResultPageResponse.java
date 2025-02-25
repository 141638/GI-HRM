package com.gi.hrm.query.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultPageResponse<T> {
    private Long totalItems;

    private Integer totalPages;

    private Integer pageSize;

    private Integer currentPage;

    private List<T> items;

    public static <T> ResultPageResponse<T> fromPage(Page<T> page) {
        return new ResultPageResponse<>(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSize(),
                page.getNumber(),
                page.getContent()
        );
    }

    public static <T, S> ResultPageResponse<S> fromPage(Page<T> page, Function<T, S> transformer) {
        if (Objects.isNull(transformer)) {
            throw new IllegalArgumentException("Transformer should not be null");
        }
        return new ResultPageResponse<>(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSize(),
                page.getNumber(),
                page.getContent().stream().map(transformer).toList()
        );
    }
}