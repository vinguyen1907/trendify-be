package com.project.uit.trendify.repository;

import com.project.uit.trendify.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    Page<ReviewEntity> findAllByProductId(Long productId, Pageable pageable);

    ReviewEntity findByOrderItemId(Long orderItemId);
}