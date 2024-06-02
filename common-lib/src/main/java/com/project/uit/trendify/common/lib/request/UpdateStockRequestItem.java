package com.project.uit.trendify.common.lib.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStockRequestItem {
    private Long productId;
    private Integer quantity;
}
