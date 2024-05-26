package com.project.uit.trendify.service.interfaces;

import com.project.uit.trendify.entity.CartEntity;
import com.project.uit.trendify.entity.CartItemEntity;

public interface ICartService {
    CartEntity getCartByUserId(Long userId);
    CartItemEntity addItemToCart(Long userId, Long productId, int quantity, String color, String size);
    CartEntity removeItemFromCart(Long userId, Long itemId);
}
