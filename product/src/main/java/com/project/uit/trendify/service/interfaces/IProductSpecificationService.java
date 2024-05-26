package com.project.uit.trendify.service.interfaces;

import com.project.uit.trendify.entity.ProductSpecificationEntity;

import java.util.List;

public interface IProductSpecificationService  {
    List<ProductSpecificationEntity> getProductSpecifications(Long productId);
}
