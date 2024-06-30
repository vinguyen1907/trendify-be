package com.project.uit.trendify.service.interfaces;

import com.project.uit.trendify.dto.PageDTO;
import com.project.uit.trendify.entity.ReviewEntity;
import org.springframework.data.domain.Page;

public interface IReviewService {
    void createReview(Long userId,
                      String nameUser,
                      String avaUrl,
                      Long productId,
                      Long orderId,
                      Long orderItemId,
                      Integer rate,
                      String content);
    PageDTO<ReviewEntity> getReviewsByProductId(Long productId, Integer page, Integer size);

    ReviewEntity getReviewByOrderItemId(Long orderItemId);
}
