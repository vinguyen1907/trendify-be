package com.project.uit.trendify.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.uit.trendify.common.lib.entity.User;
import com.project.uit.trendify.common.lib.service.JwtService;
import com.project.uit.trendify.entity.CartEntity;
import com.project.uit.trendify.entity.CartItemEntity;
import com.project.uit.trendify.request.AddCartItemRequest;
import com.project.uit.trendify.request.GetCartRequest;
import com.project.uit.trendify.service.interfaces.ICartItemService;
import com.project.uit.trendify.service.interfaces.ICartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        List<CartItemEntity> initialCartData = getInitialCartData(userId);
        messagingTemplate.convertAndSend("/topic/cart/" + userId, initialCartData);
    }

    private List<CartItemEntity> getInitialCartData(Long userId) {
        List<CartItemEntity> cart = new ArrayList<>();
        cart.add(new CartItemEntity());
        cart.add(new CartItemEntity());
        cart.add(new CartItemEntity());
        return cart;
//        return cartItemService.getCartItemsByUserId(userId);
    }

    @MessageMapping("/cart/add")
    @SendTo("/topic/cart")
    public void addItemToCart(String payload) throws JsonProcessingException {
        logger.info("Add item to cart -- {}", payload);
        AddCartItemRequest request = objectMapper.readValue(payload, AddCartItemRequest.class);
        CartItemEntity cartItem = cartService.addItemToCart(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity(),
                request.getColor(),
                request.getSize()
        );
        messagingTemplate.convertAndSend("/topic/cart/" + request.getUserId(), cartItem);
    }

    @DeleteMapping("/{userId}/{itemId}")
    public CartEntity removeItemFromCart(@PathVariable Long userId, @PathVariable Long itemId) {
        CartEntity cart = cartService.removeItemFromCart(userId, itemId);
        messagingTemplate.convertAndSend("/topic/cart/" + userId, cart);
        return cart;
    }

    private Long getUserIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            User userDetails = (User) authentication.getPrincipal();
            Long userId = userDetails.getId();
            logger.info("Parse UserID from token: " + userId);
            return userId;
        }
        throw new UsernameNotFoundException("User not found");
    }
}
