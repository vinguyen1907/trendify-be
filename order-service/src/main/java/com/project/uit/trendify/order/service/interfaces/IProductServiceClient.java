package com.project.uit.trendify.order.service.interfaces;

import com.project.uit.trendify.order.dto.UpdateProductsStockDTO;

public interface IProductServiceClient {
    boolean updateProductsStock(UpdateProductsStockDTO updateProductsStockDTO);
}
