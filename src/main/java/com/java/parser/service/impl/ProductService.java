package com.java.parser.service.impl;


import com.java.parser.common.exceptions.RecordNotFoundException;
import com.java.parser.common.utils.Mapper;
import com.java.parser.common.utils.PageUtils;
import com.java.parser.domain.common.PaginationRequest;
import com.java.parser.domain.entity.ChangeHistory;
import com.java.parser.domain.entity.ParseHistory;
import com.java.parser.domain.entity.Product;
import com.java.parser.domain.enums.ResponseMessage;
import com.java.parser.domain.response.*;
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
import java.util.Map;
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

    public PaginationResponse<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        logger.trace("Fetching all products from the database");
        PaginationRequest paginationRequest = PageUtils.mapToPaginationRequest(pageNumber, pageSize, sortBy, sortOrder);
        Pageable pageable = PageUtils.getPageable(paginationRequest);
        Page<ProductDto> page = productRepository.findAll(pageable).map(Mapper::mapToDto);
        return page.getContent().isEmpty() ?
                PageUtils.mapToPaginationResponseDto(Page.empty(), paginationRequest) :
                PageUtils.mapToPaginationResponseDto(page, paginationRequest);
    }

    public ProductDto getProductDtoBySku(String sku) {
        logger.trace("Fetching product with SKU: " + sku);
        Optional<Product> productBySku = productRepository.findBySku(sku);
        if (productBySku.isPresent()) {
            return Mapper.mapToDto(productBySku.get());
        } else {
            throw new RecordNotFoundException(ResponseMessage.RECORD_NOT_FOUND);
        }
    }

    public void logChange(String sku, String action, String requestId) {
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

    public FileUploadResponse getFileUploadResponse(String requestId) {
        ParseHistory parseHistory = parseHistoryRepository.findByRequestId(requestId);
        List<ChangeHistory> ListByRequestId = changeHistoryRepository.findByRequestId(requestId);

        Map<String, Long> actionCountMap = ListByRequestId.stream()
                .collect(Collectors.groupingBy(ChangeHistory::getAction, Collectors.counting()));

        long addedCount = actionCountMap.getOrDefault("ADDED", 0L);
        long updatedCount = actionCountMap.getOrDefault("UPDATED", 0L);
        long unchangedCount = actionCountMap.getOrDefault("UNCHANGED", 0L);

        FileUploadResponse fileUploadResponse = new FileUploadResponse();
        fileUploadResponse.setFileName(parseHistory.getFileName());
        fileUploadResponse.setRowsCount(parseHistory.getRowsCount());
        fileUploadResponse.setRequestId(requestId);
        fileUploadResponse.setTimestamp(parseHistory.getTimestamp());
        fileUploadResponse.setStatusMessage("Upload completed successfully");
        fileUploadResponse.setAddedCount(addedCount);
        fileUploadResponse.setUpdatedCount(updatedCount);
        fileUploadResponse.setUnchangedCount(unchangedCount);

        return fileUploadResponse;
    }
}
