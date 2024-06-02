package com.project.uit.trendify.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {
    private Long productId;
    private String productName;
    private Double productPrice;
    private String productImgUrl;
    private String productBrand;
    private String color;
    private String size;
    private Integer quantity;
}
