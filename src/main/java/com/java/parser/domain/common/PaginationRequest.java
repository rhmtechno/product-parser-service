package com.java.parser.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PaginationRequest {
    private Integer pageNumber = 0;
    private Integer pageSize = 20;
    private String sortBy = "id";
    private String sortOrder = "desc";
}
