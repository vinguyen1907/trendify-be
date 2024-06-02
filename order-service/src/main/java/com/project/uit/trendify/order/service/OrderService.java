package com.project.uit.trendify.order.service;

import com.project.uit.trendify.order.entity.OrderEntity;
import com.project.uit.trendify.order.entity.OrderItemEntity;
import com.project.uit.trendify.order.repository.OrderItemRepository;
import com.project.uit.trendify.order.repository.OrderRepository;
import com.project.uit.trendify.order.service.interfaces.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderEntity createOrder(OrderEntity order, List<OrderItemEntity> items) {
        OrderEntity savedOrder = orderRepository.save(order);
        items.forEach(item -> item.setOrderId(savedOrder.getId()));
        orderItemRepository.saveAll(items);
        return savedOrder;
    }

    @Override
    public List<OrderItemEntity> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }
}
