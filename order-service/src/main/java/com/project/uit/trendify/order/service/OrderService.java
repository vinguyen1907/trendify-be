package com.project.uit.trendify.order.service;

import com.project.uit.trendify.order.dto.OrderDTO;
import com.project.uit.trendify.order.entity.OrderEntity;
import com.project.uit.trendify.order.entity.OrderItemEntity;
import com.project.uit.trendify.order.enums.OrderStatus;
import com.project.uit.trendify.order.repository.OrderItemRepository;
import com.project.uit.trendify.order.repository.OrderRepository;
import com.project.uit.trendify.order.service.interfaces.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<OrderDTO> getOrdersByCustomerId(Long customerId) {
        List<OrderEntity> orders = orderRepository.findAllByCustomerId(customerId);
        List<OrderDTO> orderDTOs = new ArrayList<>();
        orders.forEach(order -> {
            var orderDTO = new OrderDTO(
                    order.getId(),
                    order.getOrderNumber(),
                    order.getCustomerId(),
                    order.getSubTotal(),
                    order.getShippingFee(),
                    order.getPromotionDiscount(),
                    order.getTotalPrice(),
                    order.getIsCompleted(),
                    order.getPaymentMethod(),
                    order.getIsPaid(),
                    order.getCurrentOrderStatus(),
                    orderItemRepository.findAllByOrderId(order.getId()),
                    order.getCreatedAt(),
                    order.getUpdatedAt()
            );
            orderDTOs.add(orderDTO);
        });
        return orderDTOs;
    }

    @Override
    public List<OrderDTO> getOrdersByCustomerIdAndStatuses(Long customerId, List<OrderStatus> statuses) {
        List<OrderEntity> orders = orderRepository.findAllByCustomerIdAndCurrentOrderStatusInOrderByUpdatedAtDesc(customerId, statuses);
        List<OrderDTO> orderDTOs = new ArrayList<>();
        orders.forEach(order -> {
            var orderDTO = new OrderDTO(
                    order.getId(),
                    order.getOrderNumber(),
                    order.getCustomerId(),
                    order.getSubTotal(),
                    order.getShippingFee(),
                    order.getPromotionDiscount(),
                    order.getTotalPrice(),
                    order.getIsCompleted(),
                    order.getPaymentMethod(),
                    order.getIsPaid(),
                    order.getCurrentOrderStatus(),
                    orderItemRepository.findAllByOrderId(order.getId()),
                    order.getCreatedAt(),
                    order.getUpdatedAt()
            );
            orderDTOs.add(orderDTO);
        });
        return orderDTOs;
    }
}
