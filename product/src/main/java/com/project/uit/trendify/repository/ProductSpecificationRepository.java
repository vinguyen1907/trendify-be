package com.project.uit.trendify.repository;

import com.project.uit.trendify.entity.ProductSpecificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSpecificationRepository extends JpaRepository<ProductSpecificationEntity, Long> {
    List<ProductSpecificationEntity> findAllByProductId(Long productId);
}
