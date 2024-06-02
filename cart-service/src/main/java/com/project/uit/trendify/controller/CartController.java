package com.project.uit.trendify.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.uit.trendify.common.lib.service.JwtService;
import com.project.uit.trendify.dto.CartItemDTO;
import com.project.uit.trendify.request.AddCartItemRequest;
import com.project.uit.trendify.request.DeleteCartItemRequest;
import com.project.uit.trendify.request.GetCartRequest;
import com.project.uit.trendify.request.UpdateCartItemQuantityRequest;
import com.project.uit.trendify.service.interfaces.ICartItemService;
import com.project.uit.trendify.service.interfaces.ICartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final ICartService cartService;
    private final ICartItemService cartItemService;
    private final SimpMessagingTemplate messagingTemplate;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MessageMapping("/cart/get")
    @SendTo("/topic/cart")
    public void getCart(String payload) throws JsonProcessingException {
        GetCartRequest request = objectMapper.readValue(payload, GetCartRequest.class);
        logger.info("Get cart -- {}", payload);
        Long userId = request.getUserId();
        sendInitialCartData(userId);
    }

    @GetMapping("/send-initial-cart")
    public void sendInitialCartData(Long userId) {
//        logger.info("Send initial cart data to session: " + sessionId);
        List<CartItemDTO> initialCartData = getInitialCartData(userId);
        messagingTemplate.convertAndSend("/topic/cart/" + userId, initialCartData);
    }

    private List<CartItemDTO> getInitialCartData(Long userId) {
        return cartItemService.getCartItemsByUserId(userId);
    }

    @MessageMapping("/cart/add")
    @SendTo("/topic/cart")
    public void addItemToCart(String payload) throws JsonProcessingException {
        logger.info("Add item to cart -- {}", payload);
        AddCartItemRequest request = objectMapper.readValue(payload, AddCartItemRequest.class);
        List<CartItemDTO> cart = cartService.addItemToCart(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity(),
                request.getColor(),
                request.getSize()
        );
        messagingTemplate.convertAndSend("/topic/cart/" + request.getUserId(), cart);
    }

    @MessageMapping("/cart/delete")
    @SendTo("/topic/cart")
    public void deleteItemFromCart(String payload) throws JsonProcessingException {
        logger.info("Delete item from cart -- {}", payload);
        DeleteCartItemRequest request = objectMapper.readValue(payload, DeleteCartItemRequest.class);
        List<CartItemDTO> cart = cartService.removeItemFromCart(
                request.getUserId(),
                request.getCartItemId()
        );
        messagingTemplate.convertAndSend("/topic/cart/" + request.getUserId(), cart);
    }

    @MessageMapping("/cart/update-quantity")
    @SendTo("/topic/cart")
    public void updateCartItemQuantity(String payload) throws JsonProcessingException {
        logger.info("Update cart item quantity -- {}", payload);
        UpdateCartItemQuantityRequest request = objectMapper.readValue(payload, UpdateCartItemQuantityRequest.class);
        List<CartItemDTO> cart = cartService.updateCartItemQuantity(
                request.getUserId(),
                request.getCartItemId(),
                request.getQuantity());
        messagingTemplate.convertAndSend("/topic/cart/" + request.getUserId(), cart);
    }
}
