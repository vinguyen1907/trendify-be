package com.project.uit.trendify.order.dto;

import com.project.uit.trendify.order.entity.OrderItemEntity;
import com.project.uit.trendify.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDTO {
    private Long id;
    private String orderNumber;
    private Long customerId;
    private Double subTotal;
    private Double shippingFee;
    private Double promotionDiscount;
    private Double totalPrice;
    private Boolean isCompleted;
    private String paymentMethod;
    private Boolean isPaid;
    private OrderStatus currentOrderStatus;
    private List<OrderItemEntity> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
