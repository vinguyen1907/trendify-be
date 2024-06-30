package com.project.uit.trendify.order.repository;

import com.project.uit.trendify.order.entity.OrderEntity;
import com.project.uit.trendify.order.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByCustomerId(Long customerId);
    List<OrderEntity> findAllByCustomerIdAndCurrentOrderStatusInOrderByUpdatedAtDesc(Long customerId, List<OrderStatus> statuses);
}
