package com.project.uit.trendify.order.controller;

import com.project.uit.trendify.common.lib.util.ExtractTokenUtil;
import com.project.uit.trendify.order.dto.OrderDTO;
import com.project.uit.trendify.order.dto.UpdateProductsStockDTO;
import com.project.uit.trendify.order.dto.UpdateProductsStockItemDTO;
import com.project.uit.trendify.order.entity.OrderEntity;
import com.project.uit.trendify.order.entity.OrderItemEntity;
import com.project.uit.trendify.order.enums.OrderStatus;
import com.project.uit.trendify.order.message.OrderPlacedMessage;
import com.project.uit.trendify.order.producer.PlaceOrderProducer;
import com.project.uit.trendify.order.request.OrderShippingAddress;
import com.project.uit.trendify.order.request.PlaceOrderRequest;
import com.project.uit.trendify.order.response.PlaceOrderResponse;
import com.project.uit.trendify.order.service.interfaces.IOrderService;
import com.project.uit.trendify.order.service.interfaces.IProductServiceClient;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private final IOrderService orderService;
    private final IProductServiceClient productServiceClient;
    private final PlaceOrderProducer placeOrderProducer;
    private final Map<String, Bucket> rateLimitBuckets;
    private final ExtractTokenUtil extractTokenUtil;

    @PostMapping("")
    public ResponseEntity<PlaceOrderResponse> createOrder(@RequestBody PlaceOrderRequest request) {
        ConsumptionProbe probe = consumptionProbe();
        if (probe.isConsumed()) {
            LOGGER.info("Receive POST /api/v1/order: {}", request);
            boolean updateSuccessful = productServiceClient.updateProductsStock(
                    new UpdateProductsStockDTO(request.getOrderItems().stream()
                            .map(item -> new UpdateProductsStockItemDTO(item.getProductId(), item.getQuantity()))
                            .toList())
            );
            if (!updateSuccessful) {
                LOGGER.error("Failed to update products stock");
                return ResponseEntity.badRequest().build();
            }
            OrderEntity order = OrderEntity.builder()
                    .orderNumber(request.getOrderNumber())
                    .customerId(request.getCustomerId())
                    .subTotal(request.getSubtotal())
                    .shippingFee(request.getShippingFee())
                    .promotionDiscount(request.getPromotionDiscount())
                    .totalPrice(request.getTotalPrice())
                    .isCompleted(false)
                    .paymentMethod(request.getPaymentMethod())
                    .isPaid(request.getIsPaid())
                    .currentOrderStatus(updateSuccessful ? OrderStatus.PENDING : OrderStatus.FAILED)
                    .build();
            OrderShippingAddress shippingAddress = request.getShippingAddress();
            List<OrderItemEntity> items = request.getOrderItems().stream()
                    .map(item -> OrderItemEntity.builder()
                            .productId(item.getProductId())
                            .productName(item.getProductName())
                            .productPrice(item.getProductPrice())
                            .productImgUrl(item.getProductImgUrl())
                            .productBrand(item.getProductBrand())
                            .quantity(item.getQuantity())
                            .size(item.getSize())
                            .color(item.getColor())
                            .build())
                    .toList();
            var createdOrder = orderService.createOrder(order, items);
            OrderPlacedMessage message = new OrderPlacedMessage(
                    createdOrder.getId(),
                    createdOrder.getOrderNumber(),
                    createdOrder.getCustomerId(),
                    createdOrder.getTotalPrice(),
                    new OrderShippingAddress(
                            shippingAddress.getRecipientName(),
                            shippingAddress.getStreet(),
                            shippingAddress.getCity(),
                            shippingAddress.getState(),
                            shippingAddress.getCountry(),
                            shippingAddress.getZipCode(),
                            shippingAddress.getCountryCallingCode(),
                            shippingAddress.getPhoneNumber(),
                            shippingAddress.getLatitude(),
                            shippingAddress.getLongitude()
                    ),
                    items
            );
            placeOrderProducer.send(message);

            LOGGER.info("Order created: {}", createdOrder);
            return ResponseEntity.ok(new PlaceOrderResponse(createdOrder.getId()));
        } else {
            LOGGER.warn("Too many requests to user-service");
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<OrderItemEntity>> getOrderItems(@PathVariable Long id) {
        ConsumptionProbe probe = consumptionProbe();
        if (probe.isConsumed()) {
            LOGGER.info("GET /api/v1/order/{}/items", id);
            List<OrderItemEntity> items = orderService.getOrderItemsByOrderId(id);
            LOGGER.info("Response GET /api/v1/order/{}/items: {}", id, items);
            return ResponseEntity.ok(items);
        } else {
            LOGGER.warn("Too many requests to user-service");
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

    ConsumptionProbe consumptionProbe() {
        Bucket bucket = rateLimitBuckets.get("order-service");
        return bucket.tryConsumeAndReturnRemaining(1);
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, @RequestParam(required = false) OrderStatus status) {
        Long userId = extractTokenUtil.getUserIdFromToken();
        LOGGER.info("GET /api/v1/order/user/{}", userId);
        List<OrderDTO> orders = orderService.getOrdersByCustomerId(userId);
        LOGGER.info("Response GET /api/v1/order/user/{}: {}", userId, orders);
        return ResponseEntity.ok(orders);
    }
}
