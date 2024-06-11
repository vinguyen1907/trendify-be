package com.project.uit.trendify.order.repository;

import com.project.uit.trendify.order.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    List<OrderItemEntity> findAllByOrderId(Long orderId);
    List<OrderItemEntity> findAllByOrderIdIn(List<Long> orderIds);
}
