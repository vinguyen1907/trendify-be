package com.project.uit.trendify.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateReviewRequest {
    @Column(name = "user_id", nullable = false)
    private Long userId;

    private String userName;

    private String avaUrl;

    private Long productId;

    private Long orderId;

    private Long orderItemId;

    private Integer rate;

    private String content;
}
