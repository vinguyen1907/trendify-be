package com.project.uit.trendify.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddCartItemRequest {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("product_id")
    private Long productId;
    private int quantity;
    private String color;
    private String size;
}
