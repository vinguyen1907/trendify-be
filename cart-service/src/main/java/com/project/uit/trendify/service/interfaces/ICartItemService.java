package com.project.uit.trendify.service.interfaces;

import com.project.uit.trendify.entity.CartItemEntity;

import java.util.List;

public interface ICartItemService {
    List<CartItemEntity> getCartItemsByUserId(Long userId);
}
