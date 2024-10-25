package com.java.parser.api;


import com.java.parser.common.annotations.NoLogging;
import com.java.parser.common.utils.ResponseUtils;
import com.java.parser.domain.common.ApiResponse;
import com.java.parser.domain.enums.ResponseMessage;
import com.java.parser.domain.response.*;
import com.java.parser.service.impl.ProductService;
import com.java.parser.service.impl.XlsxParserImplService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@NoLogging
public class ProductController extends BaseResource {

    private final XlsxParserImplService xlsxParserImplService;
    private final ProductService productService;

    @PostMapping("/upload")
    public ApiResponse<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseUtils.createResponseObject(getMessage(ResponseMessage.OPERATION_SUCCESSFUL), xlsxParserImplService.parse(file.getInputStream(), file.getOriginalFilename())
);
    }

    @GetMapping
    public ApiResponse<PaginationResponse<ProductDto>> getAllProducts(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                                      @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                                      @RequestParam(required = false, defaultValue = "asc") String sortOrder) {

        return ResponseUtils.createResponseObject(getMessage(ResponseMessage.OPERATION_SUCCESSFUL), productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder));

    }

    @GetMapping("/{sku}")
    public ApiResponse<ProductDto> getProductBySku(@PathVariable String sku) {
        return ResponseUtils.createResponseObject(getMessage(ResponseMessage.OPERATION_SUCCESSFUL), productService.getProductDtoBySku(sku)
        );

    }

    @GetMapping("/change-history")
    public ApiResponse<PaginationResponse<ChangeHistoryDto>> getChangeHistory(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                                              @RequestParam(required = false, defaultValue = "timestamp") String sortBy,
                                                                              @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return ResponseUtils.createResponseObject(getMessage(ResponseMessage.OPERATION_SUCCESSFUL), productService.getChangeHistory(pageNumber, pageSize, sortBy, sortOrder));

    }

    @GetMapping("/parse-history")
    public ApiResponse<PaginationResponse<ParseHistoryDto>> geParseHistory(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                                           @RequestParam(required = false, defaultValue = "timestamp") String sortBy,
                                                                           @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return ResponseUtils.createResponseObject(getMessage(ResponseMessage.OPERATION_SUCCESSFUL), productService.getParseHistory(pageNumber, pageSize, sortBy, sortOrder));

    }
}
