package com.project.uit.trendify.service;

import com.project.uit.trendify.common.lib.entity.Product;
import com.project.uit.trendify.dto.PageDTO;
import com.project.uit.trendify.entity.ReviewEntity;
import com.project.uit.trendify.repository.ProductRepository;
import com.project.uit.trendify.repository.ReviewRepository;
import com.project.uit.trendify.service.interfaces.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void createReview(Long userId,
                             String nameUser,
                             String avaUrl,
                             Long productId,
                             Long orderId,
                             Long orderItemId,
                             Integer rate,
                             String content) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setUserId(userId);
        reviewEntity.setUserName(nameUser);
        reviewEntity.setAvaUrl(avaUrl);
        reviewEntity.setProductId(productId);
        reviewEntity.setOrderId(orderId);
        reviewEntity.setOrderItemId(orderItemId);
        reviewEntity.setRate(rate);
        reviewEntity.setContent(content);
        reviewRepository.save(reviewEntity);

        double currentScore = product.getReviewScore();
        int currentCount = product.getReviewCount();
        double newScore = (currentScore * currentCount + rate) / (currentCount + 1);
        int newCount = currentCount + 1;
        product.setReviewScore(newScore);
        product.setReviewCount(newCount);
        productRepository.save(product);
    }

    @Override
    public PageDTO<ReviewEntity> getReviewsByProductId(Long productId, Integer page, Integer size) {
        var reviews = reviewRepository.findAllByProductId(productId, PageRequest.of(page, size));
        return new PageDTO<>(reviews);
    }

    @Override
    public ReviewEntity getReviewByOrderItemId(Long orderItemId) {
        return reviewRepository.findByOrderItemId(orderItemId);
    }
}
