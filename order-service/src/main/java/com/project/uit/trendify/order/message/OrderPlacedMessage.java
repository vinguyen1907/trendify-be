package com.project.uit.trendify.order.message;

import com.project.uit.trendify.order.entity.OrderItemEntity;
import com.project.uit.trendify.order.request.OrderShippingAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderPlacedMessage {
    private Long orderId;
    private String orderNumber;
    private Long customerId;
    private Double totalPrice;
    private OrderShippingAddress shippingAddress;
    private List<OrderItemEntity> orderItems;
}
