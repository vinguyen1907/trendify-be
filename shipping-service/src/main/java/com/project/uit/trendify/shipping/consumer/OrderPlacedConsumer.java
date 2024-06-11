package com.project.uit.trendify.shipping.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.uit.trendify.order.message.OrderPlacedMessage;
import com.project.uit.trendify.shipping.enums.ShippingStatus;
import com.project.uit.trendify.shipping.service.ShippingStatusService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.project.uit.trendify.common.lib.constant.TopicConstant.ORDER_SERVICE_ORDER_PLACED_TOPIC;

@Service
@RequiredArgsConstructor
public class OrderPlacedConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderPlacedConsumer.class);
    private final ShippingStatusService shippingStatusService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = ORDER_SERVICE_ORDER_PLACED_TOPIC, groupId = "group-1")
    public void consume(String message) {
        OrderPlacedMessage convertedMsg = objectMapper.convertValue(message, OrderPlacedMessage.class);
        LOGGER.info(String.format("#### -> Consumed message -> %s", message));
        shippingStatusService.createShippingStatus(convertedMsg.getOrderId(), ShippingStatus.PENDING, null, null, null, null);
        LOGGER.info("Init shipping status for order {}", convertedMsg.getOrderId());
    }
}
