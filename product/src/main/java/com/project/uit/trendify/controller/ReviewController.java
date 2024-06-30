package com.project.uit.trendify.controller;

import com.project.uit.trendify.common.lib.util.ExtractTokenUtil;
import com.project.uit.trendify.dto.PageDTO;
import com.project.uit.trendify.entity.ReviewEntity;
import com.project.uit.trendify.request.CreateReviewRequest;
import com.project.uit.trendify.service.interfaces.IReviewService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);
    private final IReviewService reviewService;
    private final ExtractTokenUtil extractTokenUtil;

    @GetMapping("/product/{productId}")
    public ResponseEntity<PageDTO<ReviewEntity>> getReviewsByProductId(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long userId = extractTokenUtil.getUserIdFromToken();
        LOGGER.info("/GET /api/v1/reviews - User: {}", userId);

        var response = reviewService.getReviewsByProductId(productId, page, size);
        LOGGER.info("Response /GET /api/v1/reviews: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createReview(
            @RequestBody CreateReviewRequest request
    ) {
        Long userId = extractTokenUtil.getUserIdFromToken();
        LOGGER.info("/POST /api/v1/reviews - User: {} - Request: {}", userId, request);

        reviewService.createReview(
                userId,
                request.getUserName(),
                request.getAvaUrl(),
                request.getProductId(),
                request.getOrderId(),
                request.getOrderItemId(),
                request.getRate(),
                request.getContent());

        LOGGER.info("Response /POST /api/v1/reviews: Success");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/order-item/{orderItemId}")
    public ResponseEntity<ReviewEntity> getReviewByOrderItemId(
            @PathVariable Long orderItemId
    ) {
        Long userId = extractTokenUtil.getUserIdFromToken();
        LOGGER.info("/GET /api/v1/reviews/order-item/{} - User: {}", orderItemId, userId);

        var response = reviewService.getReviewByOrderItemId(orderItemId);
        LOGGER.info("Response /GET /api/v1/reviews/order-item/{}: {}", orderItemId, response);
        return ResponseEntity.ok(response);
    }
}
