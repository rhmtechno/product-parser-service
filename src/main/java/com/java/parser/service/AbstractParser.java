package com.java.parser.service;


import com.java.parser.domain.entity.Product;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

public interface AbstractParser {
    void parse(InputStream inputStream,String fileName)throws IOException;

    default String checkProductChanges(Product product, String title, BigDecimal price, Integer quantity) {
        if (!product.getTitle().equals(title) ||
                product.getPrice().compareTo(price) != 0 ||
                !product.getQuantity().equals(quantity)) {
            return "UPDATED";
        } else {
            return "UNCHANGED";
        }
    }
}
