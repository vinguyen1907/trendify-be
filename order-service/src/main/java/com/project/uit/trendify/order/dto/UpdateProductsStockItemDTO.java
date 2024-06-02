package com.project.uit.trendify.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductsStockItemDTO {
    private Long productId;
    private Integer quantity;
}
