package com.java.parser.api;


import com.java.parser.common.annotations.NoLogging;
import com.java.parser.common.utils.ResponseUtils;
import com.java.parser.domain.common.ApiResponse;
import com.java.parser.domain.enums.ResponseMessage;
import com.java.parser.domain.response.ChangeHistoryDto;
import com.java.parser.domain.response.ProductDto;
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
    public ApiResponse<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
            xlsxParserImplService.parse(file.getInputStream(),file.getOriginalFilename());
        return ResponseUtils.createResponseObject(getMessage(ResponseMessage.OPERATION_SUCCESSFUL),"hiii");
           // return ResponseEntity.ok("File processed successfully.");

    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{sku}")
    public ResponseEntity<ProductDto> getProductBySku(@PathVariable String sku) {
        return productService.getProductDtoBySku(sku)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/change-history")
    public List<ChangeHistoryDto> getChangeHistory() {
        return productService.getChangeHistory();
    }
}
