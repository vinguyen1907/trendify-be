package com.project.uit.trendify.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "products")
@Data
public class ProductElasticDocument {
    @Id
    private Long id;
    private String code;
    private String name;
    private String description;
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
}
