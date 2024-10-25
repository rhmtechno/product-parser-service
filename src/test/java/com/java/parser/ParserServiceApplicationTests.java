package com.java.parser;
import com.java.parser.domain.entity.Product;
import com.java.parser.service.impl.ProductService;
import com.java.parser.service.impl.XlsxParserImplService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ParserServiceApplicationTests {
    @Autowired
    private XlsxParserImplService xlsxParserImplService;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        // You can initialize or reset mocks here if needed
    }

    @Test
    void parse_shouldAddNewProduct_whenProductDoesNotExist() throws Exception {
        InputStream testFile = createTestFile("SKU001", "Test Product", 99.99, 10);

        when(productService.getProductBySku("SKU001")).thenReturn(null);

        xlsxParserImplService.parse(testFile,"test");

        verify(productService, times(1)).saveOrUpdate(argThat(product ->
                "SKU001".equals(product.getSku()) &&
                        "Test Product".equals(product.getTitle()) &&
                        BigDecimal.valueOf(99.99).compareTo(product.getPrice()) == 0 &&
                        10 == product.getQuantity()
        ));
    }

    @Test
    void parse_shouldUpdateProduct_whenProductExistsAndChangesDetected() throws Exception {
        InputStream testFile = createTestFile("SKU002", "Updated Product", 150.00, 20);
        Product existingProduct = new Product("SKU002", "Old Product", BigDecimal.valueOf(100.00), 10,"rid");

        when(productService.getProductBySku("SKU002")).thenReturn(existingProduct);

        xlsxParserImplService.parse(testFile,"test");

        verify(productService, times(1)).saveOrUpdate(argThat(product ->
                "SKU002".equals(product.getSku()) &&
                        "Updated Product".equals(product.getTitle()) &&
                        BigDecimal.valueOf(150.00).compareTo(product.getPrice()) == 0 &&
                        20 == product.getQuantity()
        ));
    }

    @Test
    void parse_shouldNotUpdateProduct_whenProductExistsAndNoChangesDetected() throws Exception {
        InputStream testFile = createTestFile("SKU003", "Same Product", 200.00, 30);
        Product existingProduct = new Product("SKU003", "Same Product", BigDecimal.valueOf(200.00), 30,"rid");

        when(productService.getProductBySku("SKU003")).thenReturn(existingProduct);

        xlsxParserImplService.parse(testFile,"test");

        verify(productService, times(0)).saveOrUpdate(existingProduct);
    }

    private InputStream createTestFile(String sku, String title, double price, int quantity) throws Exception {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            var sheet = workbook.createSheet("Products");
            var header = sheet.createRow(0);
            header.createCell(0).setCellValue("SKU");
            header.createCell(1).setCellValue("Title");
            header.createCell(2).setCellValue("Price");
            header.createCell(3).setCellValue("Quantity");

            var row = sheet.createRow(1);
            row.createCell(0).setCellValue(sku);
            row.createCell(1).setCellValue(title);
            row.createCell(2).setCellValue(price);
            row.createCell(3).setCellValue(quantity);

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}