package com.java.parser.service.impl;


import com.java.parser.common.utils.Mapper;
import com.java.parser.domain.entity.Product;
import com.java.parser.domain.response.ProductDto;
import com.java.parser.repository.ProductRepository;
import com.java.parser.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService extends BaseService {
    private final ProductRepository productRepository;

    public Product getProductBySku(String sku) {
        return productRepository.findBySku(sku).orElse(null);
    }

    public void saveOrUpdate(Product product) {
        productRepository.save(product);
    }

    public List<ProductDto> getAllProducts() {
        logger.trace("Fetching all products from the database");
        return productRepository.findAll().stream().map(Mapper::mapToDto).collect(Collectors.toList());
    }

    public Optional<ProductDto> getProductDtoBySku(String sku) {
        logger.trace("Fetching product with SKU: "+ sku);
        return productRepository.findBySku(sku).map(Mapper::mapToDto);
    }

}
