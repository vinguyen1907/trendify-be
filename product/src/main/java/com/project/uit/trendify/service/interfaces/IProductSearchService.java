package com.project.uit.trendify.service.interfaces;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.entity.ProductElasticDocument;

import java.util.List;

public interface IProductSearchService {
    List<ProductDTO> searchProducts(String keyword);
    void syncProductsToElasticsearch();
}
