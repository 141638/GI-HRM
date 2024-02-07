package dto.request;

import lombok.Getter;

@Getter
public class CommonPaginatorRequest {
    private Integer pageSize;
    private Integer currentPage;
}
