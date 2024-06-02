package com.project.uit.trendify.service.interfaces;

import com.project.uit.trendify.dto.CartItemDTO;
import com.project.uit.trendify.entity.CartEntity;

import java.util.List;

public interface ICartService {
    CartEntity getCartByUserId(Long userId);
    List<CartItemDTO> addItemToCart(Long userId, Long productId, int quantity, String color, String size);
    List<CartItemDTO> removeItemFromCart(Long userId, Long itemId);
    List<CartItemDTO> updateCartItemQuantity(Long userId, Long cartItemId, Integer quantity);
}
