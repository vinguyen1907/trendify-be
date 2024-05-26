package com.project.uit.trendify.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetCartRequest {
    @JsonProperty("user_id")
    private Long userId;
}
