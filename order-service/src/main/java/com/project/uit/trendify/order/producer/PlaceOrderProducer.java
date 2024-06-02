package com.project.uit.trendify.order.producer;

import com.project.uit.trendify.order.message.OrderPlacedMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.project.uit.trendify.common.lib.constant.TopicConstant.ORDER_SERVICE_ORDER_PLACED_TOPIC;

@Service
@RequiredArgsConstructor
public class PlaceOrderProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceOrderProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(OrderPlacedMessage message) {
        LOGGER.info(String.format("#### -> Producing message -> %s", message));
        kafkaTemplate.send(ORDER_SERVICE_ORDER_PLACED_TOPIC, message);
    }
}
