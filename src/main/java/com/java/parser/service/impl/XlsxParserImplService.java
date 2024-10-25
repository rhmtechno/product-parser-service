package com.java.parser.service.impl;


import com.java.parser.common.annotations.NoLogging;
import com.java.parser.domain.entity.ParseHistory;
import com.java.parser.domain.entity.Product;
import com.java.parser.repository.ParseHistoryRepository;
import com.java.parser.service.AbstractParser;
import com.java.parser.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@NoLogging
public class XlsxParserImplService extends BaseService implements AbstractParser {

    public static final String ADDED = "ADDED";
    public static final String UPDATED = "UPDATED";
    public static final String UNCHANGED = "UNCHANGED";
    private final ProductService productService;


    @Override
    public void parse(InputStream inputStream,String fileName) throws IOException {
        String requestId = UUID.randomUUID().toString();
        int rowCount=0;
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Skip the header row
            if (rowIterator.hasNext()) {
                rowIterator.next(); // Skip the header
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // Ensure the row has enough cells
                if (row.getPhysicalNumberOfCells() < 4) {
                    logger.trace("Row "+row.getRowNum()+" does not have enough data, skipping.");
                    continue; // Skip rows that do not have enough data
                }
                rowCount++;
                // Extract data from the row
                String sku = row.getCell(0).getStringCellValue();
                String title = row.getCell(1).getStringCellValue();
                BigDecimal price = BigDecimal.valueOf(row.getCell(2).getNumericCellValue());
                Integer quantity = (int) row.getCell(3).getNumericCellValue();

                // Find existing product
                Product product = productService.getProductBySku(sku);

                if (product == null) {
                    logger.trace("Adding new product with SKU: "+ sku);
                    productService.saveOrUpdate(new Product(sku, title, price, quantity,requestId));
                    productService.logChange(sku, ADDED,requestId);
                } else {
                    String changeStatus = checkProductChanges(product, title, price, quantity);
                    switch (changeStatus) {
                        case UPDATED:
                            logger.trace("Updating product with SKU: "+ sku);
                            product.setTitle(title);
                            product.setPrice(price);
                            product.setQuantity(quantity);
                            productService.saveOrUpdate(product);
                            productService.logChange(sku, UPDATED,requestId);
                            break;
                        case UNCHANGED:
                            logger.trace("Product with SKU: {} is unchanged "+ sku);
                            productService.logChange(sku, UNCHANGED,requestId);
                            break;
                        default:
                            logger.trace("Unexpected case for SKU: "+ sku);
                    }
                }
            }
            productService.saveParseDetails(new ParseHistory(fileName, rowCount, LocalDateTime.now(),requestId));
        }
    }




}