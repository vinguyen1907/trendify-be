package com.project.uit.trendify.service;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.common.lib.util.ProcedureCallUtil;
import com.project.uit.trendify.dto.CartItemDTO;
import com.project.uit.trendify.entity.CartEntity;
import com.project.uit.trendify.entity.CartItemEntity;
import com.project.uit.trendify.repository.CartItemRepository;
import com.project.uit.trendify.repository.CartRepository;
import com.project.uit.trendify.service.interfaces.ICartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProcedureCallUtil procedureCallUtil;

    public CartEntity getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
    }

    @Override
    public List<CartItemDTO> addItemToCart(Long userId, Long productId, int quantity, String color, String size) {
        CartItemEntity cartItem = cartItemRepository.findCartItemEntityByUserIdAndProductIdAndColorAndSize(userId, productId, color, size).orElse(null);
        boolean isExist = cartItem != null;
        if (isExist) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
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
            cartItemRepository.save(cartItemEntity);
        }
        return getCartItemDTOs(userId);
    }

    @Override
    public List<CartItemDTO> removeItemFromCart(Long userId, Long itemId) {
        cartItemRepository.deleteById(itemId);
        return getCartItemDTOs(userId);
    }

    @Override
    public List<CartItemDTO> updateCartItemQuantity(Long userId, Long cartItemId, Integer quantity) {
        CartItemEntity cartItemEntity = cartItemRepository.findById(cartItemId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        cartItemEntity.setQuantity(quantity);
        cartItemRepository.save(cartItemEntity);
        return getCartItemDTOs(userId);
    }

    private List<CartItemDTO> getCartItemDTOs(Long userId) {
        List<CartItemEntity> cartItemEntities = cartItemRepository.getCartItemEntitiesByUserId(userId);
        List<Long> productIds = cartItemEntities.stream().map(CartItemEntity::getProductId).toList();
        List<ProductDTO> productDTOS = procedureCallUtil.getProductsByIdsFromProductService(productIds);
        Map<String, ProductDTO> productDTOMap = productDTOS.stream().collect(Collectors.toMap(ProductDTO::getId, product -> product));
        List<CartItemDTO> cartItemDTOS = cartItemEntities.stream().map(item -> new CartItemDTO(
                item.getId(),
                item.getUserId(),
                productDTOMap.get(item.getProductId().toString()),
                item.getQuantity(),
                item.getSize(),
                item.getColor(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        )).toList();
        return cartItemDTOS;
    }
}