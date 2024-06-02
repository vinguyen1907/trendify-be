package com.project.uit.trendify.repository;

import com.project.uit.trendify.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    List<CartItemEntity> getCartItemEntitiesByUserId(Long userId);
    Optional<CartItemEntity> findCartItemEntityByUserIdAndProductIdAndColorAndSize(Long userId, Long productId, String color, String size);
    void deleteAllByUserId(Long userId);
}
