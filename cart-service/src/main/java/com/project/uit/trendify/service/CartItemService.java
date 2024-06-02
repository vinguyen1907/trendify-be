package com.project.uit.trendify.service;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.common.lib.util.ProcedureCallUtil;
import com.project.uit.trendify.dto.CartItemDTO;
import com.project.uit.trendify.entity.CartItemEntity;
import com.project.uit.trendify.repository.CartItemRepository;
import com.project.uit.trendify.service.interfaces.ICartItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProcedureCallUtil procedureCallUtil;

    @Override
    public List<CartItemDTO> getCartItemsByUserId(Long userId) {
        var cartItemEntities = cartItemRepository.getCartItemEntitiesByUserId(userId);
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

    @Override
    @Transactional
    public void clearCart(Long customerId) {
        cartItemRepository.deleteAllByUserId(customerId);
    }
}
