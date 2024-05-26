package com.project.uit.trendify.service.interfaces;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.dto.PageDTO;
import com.project.uit.trendify.entity.ProductSpecificationEntity;

import java.io.IOException;
import java.util.List;

public interface IProductService {
    ProductDTO getProductById(Long productId);
    PageDTO<ProductDTO> getRelatedProducts(String productCode, int page, int size) throws IOException;
    List<ProductDTO> getProductsByCodes(List<String> codes);
    List<ProductSpecificationEntity> getProductSpecifications(Long productId);
}
