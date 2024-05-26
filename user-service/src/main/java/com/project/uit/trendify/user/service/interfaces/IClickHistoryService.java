package com.project.uit.trendify.user.service.interfaces;

public interface IClickHistoryService {
    void recordClickHistory(Long userId, Long productId, String productCode);
}
