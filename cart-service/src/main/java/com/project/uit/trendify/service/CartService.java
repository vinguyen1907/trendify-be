package com.project.uit.trendify.service;

import com.project.uit.trendify.entity.CartEntity;
import com.project.uit.trendify.entity.CartItemEntity;
import com.project.uit.trendify.repository.CartItemRepository;
import com.project.uit.trendify.repository.CartRepository;
import com.project.uit.trendify.service.interfaces.ICartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartEntity getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
    }

    @Override
    public CartItemEntity addItemToCart(Long userId, Long productId, int quantity, String color, String size) {
        CartItemEntity cartItem = cartItemRepository.findCartItemEntityByUserIdAndProductIdAndColorAndSize(userId, productId, color, size).orElse(null);
        boolean isExist = cartItem != null;
        if (isExist) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            return cartItemRepository.save(cartItem);
        } else {
            CartItemEntity cartItemEntity = new CartItemEntity(
                    null,
                    userId,
                    productId,
                    quantity,
                    size,
                    color,
                    null,
                    null
            );
            return cartItemRepository.save(cartItemEntity);
        }
    }

    @Override
    public CartEntity removeItemFromCart(Long userId, Long itemId) {
        return null;
    }
}