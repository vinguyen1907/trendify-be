package com.project.uit.trendify.service;

import com.project.uit.trendify.entity.ProductSpecificationEntity;
import com.project.uit.trendify.repository.ProductSpecificationRepository;
import com.project.uit.trendify.service.interfaces.IProductSpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSpecificationService implements IProductSpecificationService {
    private final ProductSpecificationRepository productSpecificationRepository;
    @Override
    public List<ProductSpecificationEntity> getProductSpecifications(Long productId) {
        return productSpecificationRepository.findAllByProductId(productId);
    }
}
