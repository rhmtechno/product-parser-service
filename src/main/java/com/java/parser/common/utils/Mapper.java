package com.java.parser.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.parser.domain.entity.Product;
import com.java.parser.domain.response.ProductDto;


public class Mapper {
    public static ProductDto mapToDto(Product product) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(product, ProductDto.class);
    }
}
