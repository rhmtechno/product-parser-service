package com.java.parser.service.impl;


import com.java.parser.common.utils.Mapper;
import com.java.parser.common.utils.PageUtils;
import com.java.parser.domain.common.PaginationRequest;
import com.java.parser.domain.entity.ChangeHistory;
import com.java.parser.domain.entity.ParseHistory;
import com.java.parser.domain.entity.Product;
import com.java.parser.domain.response.ChangeHistoryDto;
import com.java.parser.domain.response.PaginationResponse;
import com.java.parser.domain.response.ParseHistoryDto;
import com.java.parser.domain.response.ProductDto;
import com.java.parser.repository.ChangeHistoryRepository;
import com.java.parser.repository.ParseHistoryRepository;
import com.java.parser.repository.ProductRepository;
import com.java.parser.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService extends BaseService {
    private final ProductRepository productRepository;
    private final ChangeHistoryRepository changeHistoryRepository;
    private final ParseHistoryRepository parseHistoryRepository;


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

    public void logChange(String sku, String action,String requestId) {
        ChangeHistory changeHistory = new ChangeHistory();
        changeHistory.setSku(sku);
        changeHistory.setAction(action);
        changeHistory.setTimestamp(LocalDateTime.now());
        changeHistory.setRequestId(requestId);
        changeHistoryRepository.save(changeHistory);
    }

    public PaginationResponse<ChangeHistoryDto> getChangeHistory(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        PaginationRequest paginationRequest = PageUtils.mapToPaginationRequest(pageNumber, pageSize, sortBy, sortOrder);
        Pageable pageable = PageUtils.getPageable(paginationRequest);
        Page<ChangeHistoryDto> page = changeHistoryRepository.findAll(pageable).map(Mapper::changeHistoryDto);
        return page.getContent().isEmpty() ?
                PageUtils.mapToPaginationResponseDto(Page.empty(), paginationRequest) :
                PageUtils.mapToPaginationResponseDto(page, paginationRequest);

    }

    public PaginationResponse<ParseHistoryDto> getParseHistory(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        PaginationRequest paginationRequest = PageUtils.mapToPaginationRequest(pageNumber, pageSize, sortBy, sortOrder);
        Pageable pageable = PageUtils.getPageable(paginationRequest);
        Page<ParseHistoryDto> page = parseHistoryRepository.findAll(pageable).map(Mapper::parseHistoryDto);
        return page.getContent().isEmpty() ?
                PageUtils.mapToPaginationResponseDto(Page.empty(), paginationRequest) :
                PageUtils.mapToPaginationResponseDto(page, paginationRequest);
    }

    public void saveParseDetails(ParseHistory parseHistory) {
        parseHistoryRepository.save(parseHistory);
    }
}
