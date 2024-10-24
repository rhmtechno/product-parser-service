package com.java.parser.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "PRODUCT_TABLE")
@RequiredArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public Product(String sku, String title, BigDecimal price, Integer quantity) {
        this.sku = sku;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }
}