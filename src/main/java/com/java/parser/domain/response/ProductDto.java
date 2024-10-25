package com.java.parser.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
    private String sku;
    private String title;
    private BigDecimal price;
    private Integer quantity;
    private String requestId;
    private Date updatedDate;
    private Date createdDate;
}