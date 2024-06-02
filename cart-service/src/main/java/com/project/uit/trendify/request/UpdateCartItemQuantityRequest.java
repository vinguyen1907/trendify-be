package com.project.uit.trendify.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateCartItemQuantityRequest {
    @JsonProperty("user_id")
    @NotNull
    private Long userId;
    @JsonProperty("cart_item_id")
    @NotNull
    private Long cartItemId;
    @Positive
    private Integer quantity;
}
