package com.project.uit.trendify.service.interfaces;

import com.project.uit.trendify.dto.CartItemDTO;
import com.project.uit.trendify.entity.CartItemEntity;

import java.util.List;

public interface ICartItemService {
    List<CartItemDTO> getCartItemsByUserId(Long userId);

    void clearCart(Long customerId);
}
