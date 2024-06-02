package com.project.uit.trendify.user.repository;

import com.project.uit.trendify.user.entity.ProductClickHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClickHistoryRepository extends JpaRepository<ProductClickHistoryEntity, Long> {
    Optional<ProductClickHistoryEntity> findByUserIdAndProductId(Long userId, Long productId);
    List<ProductClickHistoryEntity> findTop10ByUserIdOrderByTimestampDesc(Long userId);
}
