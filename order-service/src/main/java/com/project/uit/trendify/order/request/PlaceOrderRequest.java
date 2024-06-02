package com.project.uit.trendify.order.request;

import com.project.uit.trendify.order.enums.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderRequest {
    private String orderNumber;
    private Long customerId;
    private Double subtotal;
    private Double shippingFee;
    private Double promotionDiscount;
    private Double totalPrice;
    private String paymentMethod;
    private Boolean isPaid;
    private OrderStatus currentOrderStatus;
    private OrderShippingAddress shippingAddress;
    private List<OrderItemRequest> orderItems;
}
