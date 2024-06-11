package com.project.uit.trendify.order.service.interfaces;

import com.project.uit.trendify.order.dto.OrderDTO;
import com.project.uit.trendify.order.entity.OrderEntity;
import com.project.uit.trendify.order.entity.OrderItemEntity;

import java.util.List;

public interface IOrderService {
    OrderEntity createOrder(OrderEntity order, List<OrderItemEntity> items);
    List<OrderItemEntity> getOrderItemsByOrderId(Long orderId);
    List<OrderDTO> getOrdersByCustomerId(Long customerId);
}
