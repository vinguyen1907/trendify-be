package com.project.uit.trendify.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "order_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;
    private Double productPrice;
    private String productImgUrl;
    private String productBrand;
    private String color;
    private String size;
    private Integer quantity;
}
