package com.project.uit.trendify.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetRecommendedProductsRequest {
    @JsonProperty("shoe_id")
    private String productCode;
}
