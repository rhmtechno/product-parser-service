package com.java.parser.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.parser.domain.entity.ChangeHistory;
import com.java.parser.domain.entity.ParseHistory;
import com.java.parser.domain.entity.Product;
import com.java.parser.domain.response.ChangeHistoryDto;
import com.java.parser.domain.response.ParseHistoryDto;
import com.java.parser.domain.response.ProductDto;


public class Mapper {
    public static ProductDto mapToDto(Product product) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(product, ProductDto.class);
    }


    public static ChangeHistoryDto changeHistoryDto(ChangeHistory changeHistory) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(changeHistory, ChangeHistoryDto.class);
    }

    public static ParseHistoryDto parseHistoryDto(ParseHistory parseHistory) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(parseHistory, ParseHistoryDto.class);
    }
}
