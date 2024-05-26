package com.project.uit.trendify.common.lib.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "product_image")
@Data
public class ProductImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String url;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
