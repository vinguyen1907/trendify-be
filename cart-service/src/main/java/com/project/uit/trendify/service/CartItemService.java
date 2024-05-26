package com.project.uit.trendify.service;

import com.project.uit.trendify.entity.CartItemEntity;
import com.project.uit.trendify.repository.CartItemRepository;
import com.project.uit.trendify.service.interfaces.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;

    @Override
    public List<CartItemEntity> getCartItemsByUserId(Long userId) {
        return cartItemRepository.getCartItemEntitiesByUserId(userId);
    }
}
