package com.project.uit.trendify.repository;

import com.project.uit.trendify.common.lib.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {
    List<ProductImageEntity> findAllByProductId(Long productId);
}
