package com.project.uit.trendify.consumer;

import com.project.uit.trendify.dto.CartItemDTO;
import com.project.uit.trendify.order.message.OrderPlacedMessage;
import com.project.uit.trendify.service.interfaces.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.uit.trendify.common.lib.constant.TopicConstant.ORDER_SERVICE_ORDER_PLACED_TOPIC;

@Service
@RequiredArgsConstructor
public class OrderPlacedConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderPlacedConsumer.class);
    private final ICartItemService cartItemService;
    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = ORDER_SERVICE_ORDER_PLACED_TOPIC, groupId = "group-1")
    public void consume(OrderPlacedMessage message) throws InterruptedException {
        LOGGER.info(String.format("#### -> Consumed message -> %s", message));
        cartItemService.clearCart(message.getCustomerId());
        List<CartItemDTO> cartItemDTOList = cartItemService.getCartItemsByUserId(message.getCustomerId());
        messagingTemplate.convertAndSend("/topic/cart/" + message.getCustomerId(), cartItemDTOList);
        LOGGER.info("Cleared cart for customer {}", message.getCustomerId());
    }
}
