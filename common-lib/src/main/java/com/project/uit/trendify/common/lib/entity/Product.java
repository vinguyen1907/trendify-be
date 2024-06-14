package com.project.uit.trendify.common.lib.entity;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String name;
    private String description;
    @OneToMany(mappedBy = "product")
    private List<ProductImageEntity> images;
    private String brand;
    private Long categoryId;
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

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public ProductDTO toDTO() {
        return new ProductDTO(this);
    }
}
