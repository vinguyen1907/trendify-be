package com.project.uit.trendify.user.service;

import com.project.uit.trendify.user.entity.ProductClickHistoryEntity;
import com.project.uit.trendify.user.repository.ClickHistoryRepository;
import com.project.uit.trendify.user.service.interfaces.IClickHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClickHistoryService implements IClickHistoryService {
    private final ClickHistoryRepository clickHistoryRepository;

    public void recordClickHistory(Long userId, Long productId, String productCode) {
        Optional<ProductClickHistoryEntity> record = clickHistoryRepository.findByUserIdAndProductId(userId, productId);
        boolean isExist = record.isPresent();
        if (isExist) {
            ProductClickHistoryEntity entity = record.get();
            entity.setTimestamp(LocalDateTime.now());
            clickHistoryRepository.save(entity);
        } else {
            ProductClickHistoryEntity entity = new ProductClickHistoryEntity();
            entity.setUserId(userId);
            entity.setProductId(productId);
            entity.setProductCode(productCode);
            clickHistoryRepository.save(entity);
        }
    }
}
