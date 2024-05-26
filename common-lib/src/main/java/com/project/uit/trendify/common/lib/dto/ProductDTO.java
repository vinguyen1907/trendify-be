package com.project.uit.trendify.common.lib.dto;

import com.project.uit.trendify.common.lib.entity.Product;
import com.project.uit.trendify.common.lib.entity.ProductImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductDTO {
    private String id;
    private String code;
    private String name;
    private String description;
    private List<String> imageUrls;
    private String brand;
    private Double price;
    private String outerMaterial;
    private String innerMaterial;
    private String sole;
    private String closure;
    private String heelHeight;
    private String heelType;
    private String shoeWidth;
    private Integer reviewCount;
    private Double reviewScore;
    private Integer soldCount;
    private Integer stockCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductDTO(Product product) {
        this.id = product.getId().toString();
        this.code = product.getCode();
        this.name = product.getName();
        this.description = product.getDescription();
        this.imageUrls = product.getImages().stream().map(ProductImageEntity::getUrl).toList();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.outerMaterial = product.getOuterMaterial();
        this.innerMaterial = product.getInnerMaterial();
        this.sole = product.getSole();
        this.closure = product.getClosure();
        this.heelHeight = product.getHeelHeight();
        this.heelType = product.getHeelType();
        this.shoeWidth = product.getShoeWidth();
        this.reviewCount = product.getReviewCount();
        this.reviewScore = product.getReviewScore();
        this.soldCount = product.getSoldCount();
        this.stockCount = product.getStockCount();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }
}
