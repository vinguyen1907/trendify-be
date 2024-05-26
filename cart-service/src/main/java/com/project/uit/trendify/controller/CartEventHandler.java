package com.project.uit.trendify.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.uit.trendify.service.CartService;
import com.project.uit.trendify.service.interfaces.ICartService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CartEventHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(CartEventHandler.class);
    private final ICartService cartService;

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws IOException {
        logger.info("Connected: " + session.getPrincipal().getName());
        session.sendMessage(new TextMessage(getInitialCartData()));
    }

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) throws IOException {
        logger.info("Received message: " + message.getPayload());
        JsonNode jsonNode = new ObjectMapper().readTree(message.getPayload());
        String action = jsonNode.get("action").asText();
        if ("get_cart".equals(action)) {
            logger.info("Get cart -- {}", session.getPrincipal().getName());
            cartService.getCartByUserId(Long.valueOf(Objects.requireNonNull(session.getPrincipal()).getName()));
            session.sendMessage(new TextMessage("Get cart"));
        } else if ("add_item".equals(action)) {
            logger.info("Add item");
        } else if ("remove_item".equals(action)) {
            logger.info("Remove item");
        }
        session.sendMessage(new TextMessage("request received"));
    }

    private String getInitialCartData() {
        // Truy vấn dữ liệu giỏ hàng từ database và trả về dưới dạng JSON
        // Ví dụ:
        return "{\"cartItems\": []}"; // Đây là dữ liệu mẫu
    }
}
