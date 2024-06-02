package com.project.uit.trendify.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteCartItemRequest {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("cart_item_id")
    private Long cartItemId;
}
